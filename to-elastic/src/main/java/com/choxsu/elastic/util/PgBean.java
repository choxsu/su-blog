package com.choxsu.elastic.util;

import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * @author chox su
 * @date 2018/02/27 15:09
 */
public class PgBean {

    /**
     * 获取分页对象
     *
     * @param totalRecord 总条数
     * @param pageNumber  当前页
     * @param pageSize    每页显示条数
     * @param list        array对象
     * @return 实例分页对象
     */
    public static Page getPage(List list, int pageNumber, int pageSize, int totalRecord) {
        if (pageSize == 0){
            throw new IllegalArgumentException("param pageSize mast greater than 0");
        }
        return new Page(list, pageNumber, pageSize, (totalRecord + pageSize - 1) / pageSize, totalRecord);
    }
}
