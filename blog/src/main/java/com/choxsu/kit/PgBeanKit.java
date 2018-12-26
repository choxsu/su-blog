package com.choxsu.kit;

import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chox su
 * @date 2018/02/28 9:29
 */
public class PgBeanKit<T> {

    /**
     * 获取分页对象
     *
     * @param list  list集合对象
     * @param page  当前页
     * @param size  每页条数
     * @param total 总条数
     * @param <T>   泛型参数
     * @return 分页对象
     */
    public static <T> Page<T> getPage(List<T> list, int page, int size, int total) {
        return new Page<>(list, page, size, ((total + size - 1) / size), total);
    }

    /**
     * 获取分页对象
     *
     * @param page  当前页
     * @param size  每页条数
     * @param <T>   泛型参数
     * @return 分页对象
     */
    public static <T> Page<T> getPage(int page, int size) {
        return new Page<>(new ArrayList<>(), page, size, ((size - 1) / size), 0);
    }
}
