package com.choxsu.web.front.index;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.base.BaseController;
import com.choxsu.login.LoginService;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 */
public class IndexController extends BaseController {

    @Inject
    IndexService indexService;
    @Inject
    LoginService loginService;
    public void index() {

        int page = getParaToInt("p", 1);

        Page<Record> blogPage = indexService.findBlogs(page);
        setAttr("blogs", blogPage);

        render("index.html");
    }
}
