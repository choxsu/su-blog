package com.choxsu.web.admin.controller;

import com.choxsu.common.BaseController;
import com.jfinal.kit.LogKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @author choxsu
 */
@RequiresAuthentication
public class IndexAdminController extends BaseController {

    @RequiresPermissions("后台首页权限")
    public void index() {
        LogKit.info("后台首页权限");
        render("index.html");
    }

    public static void main(String[] args) {
        String cardNo = "0680001242";
        System.out.println(cardNo.substring(0, 3));
        System.out.println(cardNo.substring(0, 3).equals("680"));
        System.out.println(cardNo.startsWith("680"));
    }
}
