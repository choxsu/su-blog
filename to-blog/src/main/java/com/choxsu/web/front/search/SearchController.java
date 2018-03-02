package com.choxsu.web.front.search;

import com.choxsu.common.BaseController;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:44
 */
public class SearchController extends BaseController {

    private static SearchService searchService = Enhancer.enhance(SearchService.class);

    public void index() {
        String keyword = getPara("keyword");
        Integer pageNumber = getParaToInt("p", 1);

        setAttr("keyword", keyword);
        Page<Record> page = searchService.search(pageNumber, 15, keyword);

        setAttr("result", page);
        render("list.html");
    }
}
