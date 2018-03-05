package com.choxsu.api.blog;

import com.choxsu.api.vo.BlogListVo;
import com.choxsu.common.BaseController;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2018/03/02 10:22
 */
@Slf4j
public class ApiBlogController extends BaseController {

    private static final ApiBlogService apiBlogService = Enhancer.enhance(ApiBlogService.class);

    /**
     * 首页博客list
     */
    public void list() {
        Integer page = getParaToInt("page", 1);
        Integer size = getParaToInt("size", 20);
        String tab = getPara("tab");
        log.info("tab==>{}", tab);
        List<BlogListVo> list = apiBlogService.list(tab, page, size);
        renderJson(getSuccessApiResult("查询成功", list));
    }
    /**
     * 博客detail
     */
    public void index() {
        Integer id = getParaToInt();
        BlogListVo blogListVo = apiBlogService.detail(id);
        renderJson(getSuccessApiResult("查询成功", blogListVo));
    }

}
