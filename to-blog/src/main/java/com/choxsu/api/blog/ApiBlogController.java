package com.choxsu.api.blog;

import com.choxsu.api.vo.BlogListVo;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.jfinal.aop.Enhancer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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


    /**
     * 博客detail
     */
    public void test() {
        List<Blog> blog = apiBlogService.test();
        renderJson(getSuccessApiResult("查询成功", blog));
    }

    /**
     * 博客detail1
     */
    public void tests() {
        List<Blog> blog = apiBlogService.test();
        renderJson(getSuccessApiResult("查询成功", blog));
    }




}
