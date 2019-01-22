package com.choxsu.util;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author choxsu
 * @date 2019/01/10
 */
public class ReleasePlanUtils {
    public static final String RELEASE_KEY = "release_key:";
    public static final String RELEASE_KEY_VO = RELEASE_KEY + "vo";
    public static final String RELEASE_KEY_NUM = RELEASE_KEY + "num";
    public static final String RELEASE_KEY_DATE = RELEASE_KEY + "date";
    private Cache redisUtil = Redis.use();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static final Date date = new Date(1546963201000L);
    private ReleaseVo releaseVo = null;

    public synchronized int getIntReleaseTime() {
        Object dateO = redisUtil.get(RELEASE_KEY_DATE);
        String currDate = dateO == null ? sdf.format(date) : dateO.toString();
        int timestamp = getTime(currDate);
        Object o = redisUtil.get(RELEASE_KEY_VO);
        if (o == null) {
            // 查询数
            Record first = Db.findFirst("SELECT*FROM otn_order_pub_release WHERE date = ? and cur_seq_num <> num limit 1", currDate);
            if (first == null) {
                throw new NullPointerException("请初始化表:otn_order_pub_release字段日期等于[" + currDate + "]的数据");
            }
            Integer num = first.getInt("num");
            redisUtil.set(RELEASE_KEY_NUM, num);
            redisUtil.set(RELEASE_KEY_DATE, currDate);
            //Integer interval_second_ = first.getInt("interval_second");
            Integer interval_second = getIs(num);
            timestamp += interval_second;
            releaseVo = new ReleaseVo();
            releaseVo.setNum(first.getInt("num"));
            releaseVo.setCurNum(1);
            releaseVo.setIntervalSecond(interval_second);
            releaseVo.setNextTime(timestamp);
            redisUtil.set(RELEASE_KEY_VO, releaseVo);
        } else {
            releaseVo = (ReleaseVo) o;
            int curNun = releaseVo.getCurNum();
            if (curNun < 1) {
                throw new IllegalArgumentException("Redis存储序列号必须大于零");
            }
            curNun++;
            timestamp = releaseVo.getNextTime() + releaseVo.getIntervalSecond();
            releaseVo.setNextTime(timestamp);
            releaseVo.setCurNum(curNun);
            redisUtil.set(RELEASE_KEY_VO, releaseVo);

            if (curNun == releaseVo.getNum()) {
                redisUtil.del(RELEASE_KEY_VO);
                int update = Db.update("update otn_order_pub_release set cur_seq_num = num where date = ? limit 1", currDate);
                if (update > 0) {
                    currDate = addOneDate(currDate);
                    redisUtil.set(RELEASE_KEY_DATE, currDate);
                    redisUtil.del(RELEASE_KEY_NUM, RELEASE_KEY_VO);
                }
            }
        }
        return timestamp;
    }

    private int getTime(String currDate) {
        if (StrKit.isBlank(currDate)) {
            throw new NullPointerException("传入日期不能为空");
        }
        Date parse;
        try {
            parse = sdf.parse(currDate);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return (int) (parse.getTime() / 1000);
    }

    /**
     * 预留500s
     *
     * @param num
     * @return
     */
    private static int getIs(int num) {
        int totalSecond = 24 * 60 * 60;
        return (totalSecond - 500) / num;
    }


    /**
     * 在yyyyMMdd基础上增加一天
     *
     * @param currDate 当前日期
     * @return
     */
    private static String addOneDate(String currDate) {
        if (StrKit.isBlank(currDate)) {
            throw new NullPointerException("传入日期不能为空");
        }
        Date parse;
        try {
            parse = sdf.parse(currDate);

        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(parse);
        calendar.add(Calendar.DATE, 1);
        parse = calendar.getTime();
        return sdf.format(parse);

    }

}
