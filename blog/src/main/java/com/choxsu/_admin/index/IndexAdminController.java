

package com.choxsu._admin.index;

import com.choxsu.common.redis.RedisKey;
import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;


/**
 * 后台管理首页
 */
public class IndexAdminController extends BaseController {

    @Inject
    IndexAdminService srv;

    private static Cache cache = Redis.use();

    public void index() {

        Ret accountProfile = cache.get(RedisKey.INDEX_KEY_PREFIX + "accountProfile");
        if (accountProfile == null) {
            accountProfile = srv.getAccountProfile();
            cache.set(RedisKey.INDEX_KEY_PREFIX + "accountProfile", accountProfile);
        }
        Ret permissionProfile = cache.get(RedisKey.INDEX_KEY_PREFIX + "permissionProfile");
        if (permissionProfile == null){
            permissionProfile = srv.getPermissionProfile();
            cache.set(RedisKey.INDEX_KEY_PREFIX + "permissionProfile", permissionProfile);
        }

        Ret blogProfile = cache.get(RedisKey.INDEX_KEY_PREFIX + "blogProfile");
        if (blogProfile == null){
            blogProfile = srv.getBlogProfile();
            cache.set(RedisKey.INDEX_KEY_PREFIX + "blogProfile", blogProfile);
        }

        Ret visitorProfile = cache.get(RedisKey.INDEX_KEY_PREFIX + "visitorProfile");
        if (visitorProfile == null){
            visitorProfile = srv.getVisitorProfile();
            cache.set(RedisKey.INDEX_KEY_PREFIX + "visitorProfile", visitorProfile);
        }
        setAttr("accountProfile", accountProfile);
        setAttr("permissionProfile", permissionProfile);
        setAttr("blogProfile", blogProfile);
        setAttr("visitorProfile", visitorProfile);
        render("index.html");
    }
}
