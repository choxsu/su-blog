

package com.choxsu._admin.index;

import com.choxsu._admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;


/**
 * 后台管理首页
 */
public class IndexAdminController extends BaseController {

    @Inject
    IndexAdminService srv;

    @Remark("后台首页")
    public void index() {

        Ret accountProfile = srv.getAccountProfile();
        Ret permissionProfile = srv.getPermissionProfile();
        Ret blogProfile = srv.getBlogProfile();
        Ret visitorProfile = srv.getVisitorProfile();
        setAttr("accountProfile", accountProfile);
        setAttr("permissionProfile", permissionProfile);
        setAttr("blogProfile", blogProfile);
        setAttr("visitorProfile", visitorProfile);
        render("index.html");
    }
}
