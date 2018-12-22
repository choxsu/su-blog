

package com.choxsu.routes;

import com.choxsu._admin.account.AccountAdminController;
import com.choxsu._admin.auth.AdminAuthInterceptor;
import com.choxsu._admin.blog.AdminBlogController;
import com.choxsu._admin.tag.AdminTagController;
import com.choxsu._admin.common.PjaxInterceptor;
import com.choxsu._admin.druid.DruidController;
import com.choxsu._admin.index.IndexAdminController;
import com.choxsu._admin.login.AdminLoginController;
import com.choxsu._admin.permission.PermissionAdminController;
import com.choxsu._admin.role.RoleAdminController;
import com.choxsu._admin.visitor.VisitorAdminController;
import com.choxsu.common.upload.UploadController;
import com.jfinal.config.Routes;

/**
 * 后台管理路由
 * 注意：自 jfinal 3.0 开始，baesViewPath 改为在 Routes 中独立配置
 * 并且支持 Routes 级别的 Interceptor，这类拦截器将拦截所有
 * 在此 Routes 中添加的 Controller，行为上相当于 class 级别的拦截器
 * Routes 级别的拦截器特别适用于后台管理这样的需要统一控制权限的场景
 * 减少了代码冗余
 */
public class AdminRoutes extends Routes {

    @Override
    public void config() {
        // 添加后台管理拦截器，将拦截在此方法中注册的所有 Controller
        addInterceptor(new AdminAuthInterceptor());
        addInterceptor(new PjaxInterceptor());

        setBaseViewPath("/_view/_admin");

        add("/login", AdminLoginController.class, "/login");

        add("/admin", IndexAdminController.class, "/index");
        add("/admin/blog", AdminBlogController.class, "/blog");
        add("/admin/tag", AdminTagController.class, "/tag");
        add("/admin/account", AccountAdminController.class, "/account");
        add("/admin/role", RoleAdminController.class, "/role");
        add("/admin/permission", PermissionAdminController.class, "/permission");
        add("/admin/visitor", VisitorAdminController.class, "/visitor");
        add("/admin/druid", DruidController.class, "/druid");
        //图片上传
        add("/admin/upload", UploadController.class);
    }
}





