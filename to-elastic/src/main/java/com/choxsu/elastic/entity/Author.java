package com.choxsu.elastic.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choxsu
 */
@Getter
@Setter
public class Author implements java.io.Serializable {
    /**
     * 作者id
     */
    private Long id;
    /**
     * 作者姓名
     */
    private String name;
    /**
     * 作者简介
     */
    private String remark;

}
