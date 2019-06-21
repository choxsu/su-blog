
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
