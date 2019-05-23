package com.choxsu.utils.kit;

import com.jfinal.kit.StrKit;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * 汉字转换拼音工具类
 *
 * @author choxsu
 * @date 2018/12/26
 */
public class PinyinKit {

    /**
     * @param str                  需要转换的汉字
     * @param firstCharToUpperCase 首字母大写 true 大写 false 小写
     * @param space                转换后的汉字拼音是否留空格 true 留空格 false 不留空格
     * @return 转换后的拼音
     */
    public static String hanyuToPinyin(String str, boolean firstCharToUpperCase, boolean space) {

        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isHanyu(c)) {
                pinyin.append(c).append(" ");
                continue;
            }
            String[] pinyinArray = null;
            try {
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyinArray != null) {
                String charPiny = firstCharToUpperCase ? StrKit.firstCharToUpperCase(pinyinArray[0]) : pinyinArray[0];
                pinyin.append(charPiny);
                if (space) {
                    pinyin.append(" ");
                }
            } else if (c != ' ') {
                pinyin.append(str.charAt(i));
            }
        }
        return pinyin.toString().trim();
    }

    /**
     * 字符是否是汉字
     *
     * @param c 字符
     * @return 字符是汉字返回true 否则返回false
     */
    public static boolean isHanyu(char c) {
        String str = String.valueOf(c);
        String regex = "^[\\u4E00-\\u9FA5]$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find();
    }
}
