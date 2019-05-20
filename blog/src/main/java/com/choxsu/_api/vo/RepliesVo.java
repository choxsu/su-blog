package com.choxsu._api.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author chox su
 * @date 2018/3/5 22:33
 */
@Getter
@Setter
public class RepliesVo {

    private Integer id;

    private AuthorVo author;

    private String[] ups;

    private Date create_at;

    private Integer reply_id;

    private boolean is_uped;

}
