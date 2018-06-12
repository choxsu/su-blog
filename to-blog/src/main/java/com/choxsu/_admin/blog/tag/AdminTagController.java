package com.choxsu._admin.blog.tag;

import com.choxsu.common.base.BaseController;

/**
 * @author chox su
 * @date 2018/6/12 22:09
 */
public class AdminTagController extends BaseController {

    AdminTagService service = AdminTagService.me;

    public void index(){

        render("index.html");
    }

}
