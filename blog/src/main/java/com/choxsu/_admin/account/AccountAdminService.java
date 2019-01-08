

package com.choxsu._admin.account;

import com.choxsu._admin.index.IndexAdminController;
import com.choxsu.common.redis.RedisKey;
import com.choxsu.common.upload.UploadService;
import com.choxsu.front.index.ArticleService;
import com.choxsu.kit.ImageKit;
import com.choxsu.util.DateUtils;
import com.jfinal.aop.Inject;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Role;
import com.choxsu.common.entity.Session;
import com.choxsu._admin.login.AdminLoginService;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.upload.UploadFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 账户管理
 */
public class AccountAdminService {

    @Inject
    AdminLoginService adminLoginService;

    private Account dao = new Account().dao();

    // 经测试对同一张图片裁切后的图片 jpg为3.28KB，而 png 为 33.7KB，大了近 10 倍
    public static final String extName = ".jpg";

    /**
     * 上传文件，以及上传后立即缩放后的文件暂存目录
     */
    public String getAvatarTempDir() {
        return "/avatar/temp/";
    }

    // 用户上传图像最多只允许 1M大小
    public int getAvatarMaxSize() {
        return 1024 * 1024;
    }


    public Page<Account> paginate(int pageNum) {
        return dao.paginate(pageNum, 10, "select *", "from account order by id desc");
    }

    public Account findById(int accountId) {
        return dao.findById(accountId);
    }

    /**
     * 注意要验证 nickName 与 userName 是否存在
     */
    public Ret update(Account account, Account loginAccount) {
        String nickName = account.getNickName().toLowerCase().trim();
        String sql = "select id from account where lower(nickName) = ? and id != ? limit 1";
        Integer id = Db.queryInt(sql, nickName, account.getId());
        if (id != null) {
            return Ret.fail("msg", "昵称已经存在，请输入别的昵称");
        }

       /* String userName = account.getUserName().toLowerCase().trim();
        sql = "select id from account where lower(userName) = ? and id != ? limit 1";
        id = Db.queryInt(sql, userName, account.getId());
        if (id != null) {
            return Ret.fail("msg", "邮箱已经存在，请输入别的昵称");
        }*/
        // 暂时只允许修改 nickName
        account.keep("id", "nickName");
        account.update();
        if (account.getId().equals(loginAccount.getId())) {
            AdminLoginService.me.reloadLoginAccount(loginAccount);
        }
        return Ret.ok("msg", "账户更新成功");
    }

    /**
     * 锁定账号
     */
    public Ret lock(int loginAccountId, int lockedAccountId) {
        if (loginAccountId == lockedAccountId) {
            return Ret.fail("msg", "不能锁定自己的账号");
        }

        int n = Db.update("update account set status = ? where id=?", Account.STATUS_LOCK_ID, lockedAccountId);

        // 锁定后，强制退出登录，避免继续搞破坏
        List<Session> sessionList = Session.dao.find("select * from session where accountId = ?", lockedAccountId);
        if (sessionList != null) {
            for (Session session : sessionList) {            // 处理多客户端同时登录后的多 session 记录
                adminLoginService.logout(session.getId());    // 清除登录 cache，强制退出
            }
        }

        if (n > 0) {
            return Ret.ok("msg", "锁定成功");
        } else {
            return Ret.fail("msg", "锁定失败");
        }
    }

    /**
     * 解锁账号
     */
    public Ret unlock(int accountId) {
        // 如果账户未激活，则不能被解锁
        int n = Db.update("update account set status = ? where status != ? and id = ?", Account.STATUS_OK, Account.STATUS_REG, accountId);
        Db.update("delete from session where accountId = ?", accountId);
        if (n > 0) {
            return Ret.ok("msg", "解锁成功");
        } else {
            return Ret.fail("msg", "解锁失败，可能是账户未激活，请查看账户详情");
        }
    }

    /**
     * 添加角色
     */
    public Ret addRole(int accountId, int roleId) {
        Record accountRole = new Record().set("accountId", accountId).set("roleId", roleId);
        Db.save("account_role", accountRole);
        return Ret.ok("msg", "添加角色成功");
    }

    /**
     * 删除角色
     */
    public Ret deleteRole(int accountId, int roleId) {
        Db.delete("delete from account_role where accountId=? and roleId=?", accountId, roleId);
        return Ret.ok("msg", "删除角色成功");
    }

    /**
     * 标记出 account 拥有的角色
     * 未来用 role left join account_role 来优化
     */
    public void markAssignedRoles(Account account, List<Role> roleList) {
        String sql = "select accountId from account_role where accountId=? and roleId=? limit 1";
        for (Role role : roleList) {
            Integer accountId = Db.queryInt(sql, account.getId(), role.getId());
            if (accountId != null) {
                // 设置 assigned 用于界面输出 checked
                role.put("assigned", true);
            }
        }
    }

    public Ret save(String userName, String password, String nickName, String ip) {
        if (StrKit.isBlank(userName) || StrKit.isBlank(password) || StrKit.isBlank(nickName)) {
            return Ret.fail("msg", "邮箱、密码或昵称不能为空");
        }
        userName = userName.toLowerCase().trim();    // 邮件全部存为小写
        password = password.trim();
        nickName = nickName.trim();

        if (nickName.contains("@") || nickName.contains("＠")) { // 全角半角都要判断
            return Ret.fail("msg", "昵称不能包含 \"@\" 字符");
        }
        if (nickName.contains(" ") || nickName.contains("　")) { // 检测是否包含半角或全角空格
            return Ret.fail("msg", "昵称不能包含空格");
        }
        if (isNickNameExists(nickName)) {
            return Ret.fail("msg", "昵称已被使用，请换一个昵称");
        }
        if (isUserNameExists(userName)) {
            return Ret.fail("msg", "邮箱已被使用，请换一个邮箱");
        }

        // 密码加盐 hash
        String salt = HashKit.generateSaltForSha256();
        password = HashKit.sha256(salt + password);

        // 创建账户
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setSalt(salt);
        account.setNickName(nickName);
        account.setStatus(Account.STATUS_OK);
        account.setCreateAt(new Date());
        account.setIp(ip);
        account.setAvatar(Account.AVATAR_NO_AVATAR);  // 注册时设置默认头像

        if (account.save()) {
            //缓存清除
            Redis.use().del(RedisKey.INDEX_KEY_PREFIX + "accountProfile");
            return Ret.ok("msg", "添加成功！");
        } else {
            return Ret.fail("msg", "添加失败！");
        }

    }


    /**
     * 用户名是否已被注册
     */
    public boolean isUserNameExists(String userName) {
        userName = userName.toLowerCase().trim();
        return Db.queryInt("select id from account where userName = ? limit 1", userName) != null;
    }


    /**
     * 昵称是否已被注册，昵称不区分大小写，以免存在多个用户昵称看起来一个样的情况
     * <p>
     * mysql 的 where 字句与 order by 子句默认不区分大小写，区分大小写需要在
     * 字段名或字段值前面使用 binary 关键字例如：
     * where nickName = binary "jfinal" 或者 where binary nickName = "jfinal"，前者性能要高
     * <p>
     * 为了避免不同的 mysql 配置破坏掉 mysql 的 where 不区分大小写的行为，这里在 sql 中使用
     * lower(...) 来处理，参数 nickName 也用 toLowerCase() 方法来处理，再次确保不区分大小写
     */
    public boolean isNickNameExists(String nickName) {
        nickName = nickName.toLowerCase().trim();
        return Db.queryInt("select id from account where lower(nickName) = ? limit 1", nickName) != null;
    }

    public Ret delete(Integer id) {
        if (id != null && id == 1) {
            return Ret.fail().set("msg", "不能删除超级管理员");
        }
        boolean b = dao.deleteById(id);
        if (b) {
            //缓存清除
            Redis.use().del(RedisKey.INDEX_KEY_PREFIX + "accountProfile");
            return Ret.ok().set("msg", "删除成功");
        }
        return Ret.fail().set("msg", "删除失败");

    }

    /**
     * 获取 "后台账户/管理员" 列表，在 account_role 表中存在的账户(被分配过角色的账户)
     * 被定义为 "后台账户/管理员"
     * <p>
     * 该功能便于查看后台都有哪些账户被分配了角色，在对账户误操作分配了角色时，也便于取消角色分配
     */
    public List<Record> getAdminList() {
        String sql = "select a.nickName, a.userName, ar.*, r.name from account a, account_role ar, role r " +
                "where a.id = ar.accountId and ar.roleId = r.id " +
                "order by roleId asc";
        return Db.find(sql);
    }


    /**
     * 上传图像到临时目录，发回路径供 jcrop 裁切
     */
    public Ret uploadAvatar(int accountId, UploadFile uf) {
        if (uf == null) {
            return Ret.fail("msg", "上传文件UploadFile对象不能为null");
        }

        try {
            if (ImageKit.notImageExtName(uf.getFileName())) {
                return Ret.fail("msg", "文件类型不正确，只支持图片类型：gif、jpg、jpeg、png、bmp");
            }

            String avatarUrl = "/upload" + getAvatarTempDir() + accountId + "_" + System.currentTimeMillis() + extName;
            String saveFile = PathKit.getWebRootPath() + avatarUrl;
            ImageKit.zoom(500, uf.getFile(), saveFile);
            return Ret.ok("avatarUrl", avatarUrl);
        } catch (Exception e) {
            return Ret.fail("msg", e.getMessage());
        } finally {
            uf.getFile().delete();
        }
    }


    public Ret saveAvatar(Account loginAccount, String avatarUrl, int x, int y, int width, int height) {
        int accountId = loginAccount.getId();
        // 暂时用的 webRootPath，以后要改成 baseUploadPath，并从一个合理的地方得到
        String webRootPath = PathKit.getWebRootPath();
        String avatarFileName = webRootPath + avatarUrl;

        try {
            // 相对路径 + 文件名：用于保存到 account.avatar 字段
            String[] relativePathFileName = new String[1];
            // 绝对路径 + 文件名：用于保存到文件系统
            String[] absolutePathFileName = new String[1];
            buildPathAndFileName(accountId, webRootPath, relativePathFileName, absolutePathFileName);

            BufferedImage bi = ImageKit.crop(avatarFileName, x, y, width, height);
            bi = ImageKit.resize(bi, 200, 200);     // 将 size 变为 200 X 200，resize 不会变改分辨率
            deleteOldAvatarIfExists(absolutePathFileName[0]);
            ImageKit.save(bi, absolutePathFileName[0]);
            //保存图片上传记录
            UploadService.me.updateUploadImage(accountId, avatarUrl,relativePathFileName[0], avatarUrl, extName, "0", "");
            updateAccountAvatar(accountId, relativePathFileName[0]);
            AdminLoginService.me.reloadLoginAccount(loginAccount);
            return Ret.ok("msg", "头像更新成功，部分浏览器需要按 CTRL + F5 强制刷新看效果").set("url", relativePathFileName[0]);
        } catch (Exception e) {
            return Ret.fail("msg", "头像更新失败：" + e.getMessage());
        } finally {
            new File(avatarFileName).delete();     // 删除用于裁切的源文件
        }
    }

    /**
     * 1：生成保存于 account.avatar 字段的：相对路径 + 文件名，存放于 relativePathFileName[0]
     * 2：生成保存于文件系统的：绝对路径 + 文件名，存放于 absolutePathFileName[0]
     * <p>
     * 3：用户头像保存于 baseUploadPath 之下的 /avatar/ 之下
     * 4：account.avatar 只存放相对于 baseUploadPath + "/avatar/" 之后的路径和文件名
     * 例如：/upload/avatar/0/123.jpg 只存放 "0/123.jpg" 这部分到 account.avatar 字段之中
     * <p>
     * 5："/avatar/" 之下生成的子录为 accountId 对 5000取整，例如 accountId 为 123 时，123 / 5000 = 0，生成目录为 "0"
     * 6：avatar 文件名为：accountId + ".jpg"
     */
    private void buildPathAndFileName(int accountId, String webRootPath, String[] relativePathFileName, String[] absolutePathFileName) {
        String relativePath = (accountId / 5000) + "/";
        String fileName = accountId + extName;
        relativePathFileName[0] = relativePath + fileName;

        String absolutePath = webRootPath + "/upload/avatar/" + relativePath;   // webRootPath 将来要根据 baseUploadPath 调整，改代码，暂时选先这样用着，着急上线
        File temp = new File(absolutePath);
        if (!temp.exists()) {
            temp.mkdirs();  // 如果目录不存在则创建
        }
        absolutePathFileName[0] = absolutePath + fileName;
    }

    /**
     * 目前该方法为空实现
     * 如果在 linux 上跑稳了，此方法可以删除，不必去实现，如果出现 bug，
     * 则尝试实现该方法，即当用户图像存在时再次上传保存，则先删除老的，
     * 以免覆盖老文件时在 linux 之上出 bug
     */
    private void deleteOldAvatarIfExists(String oldAvatar) {

    }

    public void updateAccountAvatar(int accountId, String relativePathFileName) {
        Db.update("update account set avatar=? where id=? limit 1", relativePathFileName, accountId);
    }

    public Page<Record> findLoginLog(Integer p, Integer size) {
        return Db.paginate(p, size, "SELECT l.*,a.userName,a.nickName",
                " FROM login_log l ,account a where l.accountId = a.id order by l.loginAt desc");
    }

    public Page<Record> findUploadLog(Integer p, Integer size) {
        Page<Record> paginate = Db.paginate(p, size, "select * ", "from images order by id desc");
        paginate.getList().forEach(record -> {
            record.set("username", Db.queryStr("select userName from account where id = ?", record.getInt("account_id")));
            Integer created = record.getInt("created");
            long c = created.longValue() * 1000;
            record.set("created", new Date(c));
        });

        return paginate;
    }
}
