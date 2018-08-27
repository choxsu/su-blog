package com.choxsu.api;

import com.choxsu.common.base.BaseController;
import com.choxsu.util.HttpUtil;
import com.jfinal.kit.StrKit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author choxsu
 * @date 2018/8/24 0024
 */
// @Before(CORSInterceptor.class)
public class ApiController extends BaseController {
    /**
     * 请求人民币汇率 接口
     */
    static String url = "https://hq.sinajs.cn/rn=" + new Date().getTime() + "&list=USDCNY";

    public void getCNY() {

        Map result = new HashMap();
        String s = HttpUtil.get(url, "GBK");
        if (StrKit.isBlank(s)) {
            renderJson(fail("没有查询到数据"));
            return;
        }
        String[] split = s.split("\"");
        if (split != null && split.length == 3) {
            String content = split[1];
            System.out.println(content);
            String[] cons = content.split(",");
            result.put("time", cons[10] + " " + cons[0]);
            result.put("rate", cons[1]);
            result.put("rate1", cons[2]);
            result.put("rate3", cons[3]);
            result.put("num", cons[4]);
            result.put("rate4", cons[5]);
            result.put("rate5", cons[6]);
            result.put("rate6", cons[7]);
            result.put("rate7", cons[8]);
            result.put("msg", cons[9]);
            renderJson(success(result));
            return;
        }
        renderJson(fail("没有查询到数据"));
    }


    public static void main(String[] args) {
        String s = HttpUtil.get(url, "GBK");
        if (StrKit.isBlank(s)) {
            return;
        }
        String[] split = s.split("\"");
        Map result = new HashMap();
        if (split != null && split.length == 3) {
            String content = split[1];
            System.out.println(content);
            String[] cons = content.split(",");
            result.put("time", cons[10] + " " + cons[0]);
            result.put("rate", cons[1]);
            result.put("rate1", cons[2]);
            result.put("rate3", cons[3]);
            result.put("num", cons[4]);
            result.put("rate4", cons[5]);
            result.put("rate5", cons[6]);
            result.put("rate6", cons[7]);
            result.put("rate7", cons[8]);
            result.put("msg", cons[9]);

        }
        System.out.println(result);

    }
}
