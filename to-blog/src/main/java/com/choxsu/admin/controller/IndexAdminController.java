package com.choxsu.admin.controller;

import com.choxsu.common.BaseController;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @author choxsu
 */
@RequiresAuthentication
public class IndexAdminController extends BaseController {

    @RequiresPermissions("后台首页权限")
    public void index(){

        render("index.html");
    }
}
