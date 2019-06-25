package com.choxsu.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author chox su
 * @date 2018/03/05 16:28
 */
@Getter
@Setter
public class BlogListVo {

    private Integer id;

    private Integer authorId;

    private String tab;

    private String content;

    private String title;

    private Date lastReplyAt;

    private Boolean good;

    private Boolean top;

    private Integer replyCount;

    private Integer visitCount;

    private Date createAt;

    private AuthorVo author;
}
