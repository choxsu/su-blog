package com.choxsu.api.vo;

import com.choxsu.api.entity.Author;
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

    private Integer author_id;

    private String tab;

    private String content;

    private String title;

    private Date last_reply_at;

    private Boolean good;

    private Boolean top;

    private Integer reply_count;

    private Integer visit_count;

    private Date create_at;

    private Author author;
}
