package com.choxsu._admin.blog.category;

import com.choxsu.common.base.BaseController;

/**
 * @author chox su
 * @date 2018/6/12 22:09
 */
public class AdminCategoryController extends BaseController {

    AdminCategoryService service = AdminCategoryService.me;

    public void index(){

        render("index.html");
    }

}
