package com.choxsu.kit;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
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
     * @param str
     * @return
     */
    public static String hanyuToPinyin(String str) {

        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!isHanyu(c)) {
                pinyin.append(c);
                continue;
            }
            String[] pinyinArray = null;
            try {
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyinArray != null) {
                pinyin.append(pinyinArray[0]);
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
