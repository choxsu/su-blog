

package com.choxsu._admin.index;

import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.List;

/**
 * 后台管理首页
 */
public class IndexAdminController extends BaseController {

    @Inject
    IndexAdminService srv;

    private static Cache cache = Redis.use();
    public static final String INDEX_KEY_PREFIX = "chosu:index:";

    public void index() {

        Ret accountProfile = cache.get(INDEX_KEY_PREFIX + "accountProfile");
        if (accountProfile == null) {
            accountProfile = srv.getAccountProfile();
            cache.set(INDEX_KEY_PREFIX + "accountProfile", accountProfile);
        }
        Ret permissionProfile = cache.get(INDEX_KEY_PREFIX + "permissionProfile");
        if (permissionProfile == null){
            permissionProfile = srv.getPermissionProfile();
            cache.set(INDEX_KEY_PREFIX + "permissionProfile", permissionProfile);
        }

        Ret blogProfile = cache.get(INDEX_KEY_PREFIX + "blogProfile");
        if (blogProfile == null){
            blogProfile = srv.getBlogProfile();
            cache.set(INDEX_KEY_PREFIX + "blogProfile", blogProfile);
        }

        Ret visitorProfile = cache.get(INDEX_KEY_PREFIX + "visitorProfile");
        if (visitorProfile == null){
            visitorProfile = srv.getVisitorProfile();
            cache.set(INDEX_KEY_PREFIX + "visitorProfile", visitorProfile);
        }
        setAttr("accountProfile", accountProfile);
        setAttr("permissionProfile", permissionProfile);
        setAttr("blogProfile", blogProfile);
        setAttr("visitorProfile", visitorProfile);
        render("index.html");
    }
}
