package com.choxsu.common;

import com.choxsu.web.admin.controller.IndexAdminController;
import com.choxsu.web.admin.common.interceptor.AdminAuthInterceptor;
import com.jfinal.config.Routes;

/**
 * @author choxsu
 */
public class AdminRoutes extends Routes {
    @Override
    public void config() {
        // 添加后台管理拦截器，将拦截在此方法中注册的所有 Controller
        addInterceptor(new AdminAuthInterceptor());

        setBaseViewPath("/view/admin");

        add("/admin", IndexAdminController.class, "/");
    }
}
