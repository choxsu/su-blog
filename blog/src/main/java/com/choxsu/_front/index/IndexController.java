package com.choxsu._front.index;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.choxsu._front.article.ArticleService;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;

import java.io.File;

/**
 * @author choxsu
 * @date 2019/02/14
 */
public class IndexController extends BaseController {

    @Inject
    ArticleService articleService;

    public static final String interviewName = "苏小秋_JAVA开发工程师_3年.docx";

    /**
     * 网站首页
     */
    public void index() {
        int page = getParaToInt("p", 1);
        Page<Blog> blogPage = articleService.findArticles(page, 5, null);
        setAttr("page", blogPage);
        render("index.html");
    }

    /**
     * 简历下载
     */
    @ActionKey("/resume/download")
    public void resumeDownload(){
        String webRootPath = PathKit.getWebRootPath();
        String path = "assets/interview/";
        File fileInterview = new File(webRootPath + File.separator + path + interviewName);
        renderFile(fileInterview);
    }
}
