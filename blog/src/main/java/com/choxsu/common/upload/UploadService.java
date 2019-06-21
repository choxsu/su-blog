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

import com.choxsu.common.entity.Account;
import com.choxsu.utils.kit.ImageKit;
import com.choxsu.utils.util.DateUtils;
import com.jfinal.core.JFinal;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 上传业务
 * 1：不同模块分别保存到不同子目录
 * 2：每个目录下文件数达到 5000 时创建新的子目录，upload_counter 用于记录每个模块文件上传总数
 * 用于创建子目录
 */
public class UploadService {

    public static final UploadService me = new UploadService();
    /**
     * 上传图片允许的最大尺寸，目前只允许 200KB
     */
    public static final int imageMaxSize = 200 * 1024;

    /**
     * 上传图片临时目录，相对于 baseUploadPath 的路径，是否以 "/" 并无影响
     * 本项目的 baseUploadLoad 为 /var/www/project_name/upload
     */
    public static final String uploadTempPath = "/img/temp";

    /**
     * 相对于 webRootPath 之后的目录，与"/upload" 是与 baseUploadPath 重合的部分
     */
    private static final String basePath = "/upload/img/";

    /**
     * 每个子目录允许存 5000 个文件
     */
    public static final int FILES_PER_SUB_DIR = 5000;

    /**
     * ueditor 上传业务方法
     */
    public Ret editorUpload(Account account, String uploadType, UploadFile uf) {
        Ret ret = checkUeditorUploadFile(uf);
        if (ret != null) {
            return ret;
        }

        String fileSize = uf.getFile().length() + "";
        String extName = "." + ImageKit.getExtName(uf.getFileName());

        // 相对路径 + 文件名：url 形如："/upload/img/article/0/123.jpg
        String[] relativePathFileName = new String[1];
        // 绝对路径 + 文件名：用于保存到文件系统
        String[] absolutePathFileName = new String[1];
        // 生成的文件名
        String[] fileName = new String[1];
        buildPathAndFileName(uploadType, account.getId(), extName, relativePathFileName, absolutePathFileName, fileName);
        saveOriginalFileToTargetFile(uf.getFile(), absolutePathFileName[0]);

        // 更新 upload_counter 表的 counter 字段值
        updateUploadCounter(uploadType);
        //保存图片上传记录
        updateUploadImage(account.getId(), relativePathFileName[0], fileName[0], uf.getOriginalFileName(), extName, fileSize, "");
        return Ret.by("success", 1)
                .set("message", "上传成功")
                .set("url", relativePathFileName[0])
                .set("original", uf.getOriginalFileName());

    }

    /**
     * @param accountId        上传者
     * @param url              图片路劲
     * @param name             图片名
     * @param originalFileName 原始图片名
     * @param type             文件类型
     * @param fileSize         文件大小
     * @param source           来源
     */
    public void updateUploadImage(Integer accountId,
                                   String url,
                                   String name,
                                   String originalFileName,
                                   String type,
                                   String fileSize,
                                   String source) {
        Record record = new Record();
        record.set("account_id", accountId);
        record.set("src", url);
        record.set("name", name);
        record.set("type", type);
        record.set("source", source);
        record.set("original_name", originalFileName);
        record.set("file_size", fileSize);
        record.set("created", DateUtils.getUnixNowTime());
        Db.save("images", record);
    }

    /**
     * 生成规范的文件名
     * accountId_年月日时分秒.jpg
     * 包含 accountId 以便于找到某人上传的图片，便于定位该用户所有文章，方便清除恶意上传
     * 图片中添加一些 meta 信息：accountId_201604161359.jpg
     * 目录中已经包含了模块名了，这里的 meta 只需要体现 accountId 与时间就可以了
     */
    private String generateFileName(Integer accountId, String extName) {
        return accountId + "_" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + extName;
    }

    /**
     * 根据上传类型生成完整的文件保存路径
     *
     * @param uploadType 上传类型，目前支持四种：project, share, feedback, document
     */
    private void buildPathAndFileName(
            String uploadType,
            Integer accountId,
            String extName,
            String[] relativePathFileName,
            String[] absolutePathFileName,
            String[] fileName) {
        Integer counter = 0;
        Record record = Db.findFirst("select counter from upload_counter where uploadType=? limit 1", uploadType);
        if (record == null) {
            record = new Record();
            record.set("uploadType", uploadType);
            record.set("counter", 0);
            record.set("descr", "记录" + uploadType + "模块上传图片的总数量，用于生成相对路径");
            Db.save("upload_counter", record);
        } else {
            counter = record.getInt("counter");
        }

        if (counter == null) {
            throw new IllegalArgumentException("uploadType 不正确");
        }

        String relativePath = "/" + (counter / FILES_PER_SUB_DIR) + "/";    // 生成相对对路径
        relativePath = basePath + uploadType + relativePath;

        fileName[0] = generateFileName(accountId, extName);
        relativePathFileName[0] = relativePath + fileName[0];

        String absolutePath = PathKit.getWebRootPath() + relativePath;   // webRootPath 将来要根据 baseUploadPath 调整，改代码，暂时选先这样用着，着急上线
        File temp = new File(absolutePath);
        if (!temp.exists()) {
            temp.mkdirs();  // 如果目录不存在则创建
        }
        absolutePathFileName[0] = absolutePath + fileName[0];
    }

    /**
     * 上传完成后更新 upload_counter 表
     */
    private void updateUploadCounter(String uploadType) {
        Db.update("update upload_counter set counter = counter + 1 where uploadType=? limit 1", uploadType);
    }

    /**
     * 目前使用 File.renameTo(targetFileName) 的方式保存到目标文件，
     * 如果 linux 下不支持，或者将来在 linux 下要跨磁盘保存，则需要
     * 改成 copy 文件内容的方式并删除原来文件的方式来保存
     */
    private void saveOriginalFileToTargetFile(File originalFile, String targetFile) {
        originalFile.renameTo(new File(targetFile));
    }

    /**
     * 检查 editormd 上传图片的合法性，返回值格式需要符合 editormd 的要求
     */
    private Ret checkUeditorUploadFile(UploadFile uf) {
        if (uf == null || uf.getFile() == null) {
            return Ret.create("success", 0).set("message", "上传文件为 null");
        }
        if (ImageKit.notImageExtName(uf.getFileName())) {
            uf.getFile().delete();      // 非图片类型，立即删除，避免浪费磁盘空间
            return Ret.create("success", 0).set("message", "只支持 jpg、jpeg、gif、png、bmp 四种图片类型");
        }
        if (uf.getFile().length() > imageMaxSize) {
            uf.getFile().delete();      // 图片大小超出范围，立即删除，避免浪费磁盘空间
            return Ret.create("success", 0).set("message", "图片尺寸只允许 200K 大小");
        }
        return null;
    }

    /**
     * data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAoHBwgHBgoICAgLCgoLDhgQDg0NDh0VFhEYIx8lJCIfIiEmKzcvJik0KSEiMEExNDk7Pj
     * @param dataBase64Img
     * @param originalFileName
     * @return
     */
    public UploadFile getUploadFile(String dataBase64Img, String originalFileName) {
        if (StrKit.isBlank(dataBase64Img)) {
            return null;
        }
        String inOf = "base64,";
        int dex = dataBase64Img.indexOf(inOf);
        String base64 = dataBase64Img.substring(dex + inOf.length());
        String data = dataBase64Img.substring(0, dex - 1);
        String contentType = data.split(":")[1];
        String path = PathKit.getWebRootPath() + File.separator + JFinal.me().getConstants().getBaseUploadPath() + uploadTempPath;
        String pathFile = path + File.separator + originalFileName;// 新生成的图片
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        //先写入到本地临时文件夹
        OutputStream out = null;
        try {
            out = new FileOutputStream(pathFile);
            out.write(Base64.decodeBase64(base64));
        } catch (Exception e) {
            LogKit.info(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //String parameterName, String uploadPath, String filesystemName, String originalFileName, String contentType

        return new UploadFile("base64Image",
                path,
                originalFileName,
                originalFileName,
                contentType);
    }
}
