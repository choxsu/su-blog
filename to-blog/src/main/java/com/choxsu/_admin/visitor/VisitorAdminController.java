package com.choxsu._admin.visitor;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Visitor;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author choxsu
 */
public class VisitorAdminController extends BaseController {

    VisitorAdminService visitorAdminService = VisitorAdminService.me;

    public void index() {

        Page<Visitor> visitorPage = visitorAdminService
                .paginate(getParaToInt("p", 1),
                        getParaToInt("size", 20));
        setAttr("visitorPage", visitorPage);
        render("index.html");

    }

}
