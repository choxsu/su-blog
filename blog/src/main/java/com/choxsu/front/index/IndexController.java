package com.choxsu.front.index;

import com.choxsu.common.base.BaseController;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;

import java.io.File;

/**
 * @author choxsu
 * @date 2019/02/14
 */
public class IndexController extends BaseController {

    public void index() {
        render("index.html");
    }

    public static final String interviewName = "苏小秋_JAVA开发工程师_3年.docx";

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
