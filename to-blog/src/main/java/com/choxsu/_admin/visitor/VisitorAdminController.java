package com.choxsu._admin.visitor;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.base.SortEnum;
import com.choxsu.common.entity.Visitor;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author choxsu
 */
public class VisitorAdminController extends BaseController {

    @Inject
    VisitorAdminService visitorAdminService;

    public void index() {

        Page<Visitor> visitorPage = visitorAdminService
                .paginateOrderBy(getParaToInt("p", 1),
                        getParaToInt("size", 20),
                        "requestTime",
                        SortEnum.DESC);
        setAttr("visitorPage", visitorPage);
        render("index.html");

    }

}
