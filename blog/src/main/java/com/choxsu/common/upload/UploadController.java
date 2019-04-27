/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * <p>
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * <p>
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * <p>
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.choxsu.common.upload;

import com.choxsu.common.base.BaseController;
import com.jfinal.core.JFinal;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;


/**
 * UploadController 上传控制器，接管 editormd 上传功能
 */
public class UploadController extends BaseController {

    UploadService srv = UploadService.me;

    public static final String[] uploadTypes = {"article"};

    /**
     * editormd 编辑器图片上传
     */
    public void editormdImgUpload() {

        if (notLogin()) {
            renderJson(Ret.by("success", 0).set("message", "只有登录用户才可以上传文件"));
            return;
        }

        UploadFile uploadFile = null;

        try {
            uploadFile = getFile("editormd-image-file", UploadService.uploadTempPath, UploadService.imageMaxSize);
            Ret ret = srv.editorUpload(getLoginAccount(), uploadTypes[0], uploadFile);
            // renderJson(ret);
            render(new JsonRender(ret).forIE());    // 防止 IE 下出现文件下载现象
        } catch (com.jfinal.upload.ExceededSizeException ex) {
            renderJson(Ret.by("success", 0).set("message", "上传图片只允许 200K 大小"));
        } catch (Exception e) {
            if (uploadFile != null) {
                uploadFile.getFile().delete();
            }
            renderJson(Ret.by("success", 0).set("message", "上传图片出现未知异常，请告知管理员：" + e.getMessage()));
            LogKit.error(e.getMessage(), e);
        }
    }


    /**
     * 编辑器base64图片粘贴上传
     */
    public void base64ImgUpload() {

        if (notLogin()) {
            renderJson(Ret.by("success", 0).set("message", "只有登录用户才可以上传文件"));
            return;
        }

        String dataBase64Img = getPara("base64Image");
        String imageName = getPara("imageName");
        if (StrKit.isBlank(dataBase64Img) || StrKit.isBlank(imageName)) {
            renderJson(Ret.by("success", 0).set("message", "上传图片不存在"));
            return;
        }
        UploadFile uploadFile = srv.getUploadFile(dataBase64Img, imageName);
        try {
            Ret ret = srv.editorUpload(getLoginAccount(), uploadTypes[0], uploadFile);
            render(new JsonRender(ret).forIE());    // 防止 IE 下出现文件下载现象
        } catch (Exception e) {
            renderJson(Ret.by("success", 0).set("message", "上传图片出现未知异常，请告知管理员：" + e.getMessage()));
            LogKit.error(e.getMessage(), e);
        }
    }

}
