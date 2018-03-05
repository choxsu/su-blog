package com.choxsu.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chox su
 * @date 2018/3/5 22:30
 */
@Getter
@Setter
public class BlogDetailVo extends BlogListVo {

    private List<RepliesVo> replies;

    private boolean is_collect;

}
