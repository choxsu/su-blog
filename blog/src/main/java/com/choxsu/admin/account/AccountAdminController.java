
package com.choxsu.admin.account;

import com.choxsu.admin.permission.Remark;
import com.choxsu.admin.role.RoleAdminService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Role;
import com.choxsu.utils.kit.IpKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.util.List;

/**
 * @author choxsu
 * 账户管理控制器
 */
public class AccountAdminController extends BaseController {

    @Inject
    AccountAdminService srv;
    @Inject
    RoleAdminService roleAdminService;

    @Remark("账户首页")
    public void index() {
        Page<Account> accountPage = srv.paginate(getParaToInt("p", 1));
        setAttr("accountPage", accountPage);
        render("index.html");
    }

    @Remark("账户添加页面")
    public void add() {
        keepPara("p");
        render("add.html");
    }

    @Remark("账户保存")
    @Before(AccountSaveUpdateValidator.class)
    public void save() {
        Account account = getBean(Account.class);
        Ret ret = srv.save(account.getUserName(), account.getPassword(), account.getNickName(), IpKit.getRealIp(getRequest()), false);
        renderJson(ret);
    }

    @Remark("账户删除")
    public void del() {
        Ret ret = srv.delete(getParaToInt("id"));
        renderJson(ret);
    }

    @Remark("账户编辑页面")
    public void edit() {
        keepPara("p");
        Account account = srv.findById(getParaToInt("id"));
        setAttr("account", account);
        render("edit.html");
    }

    /**
     * 提交修改
     */
    @Remark("账户更新")
    @Before(AccountSaveUpdateValidator.class)
    public void update() {
        Account account = getBean(Account.class);
        Ret ret = srv.update(account, getLoginAccount());
        renderJson(ret);
    }


    /**
     * 账户锁定
     */
    @Remark("账户锁定")
    public void lock() {
        Ret ret = srv.lock(getLoginAccountId(), getParaToInt("id"));
        renderJson(ret);
    }

    /**
     * 账户解锁
     */
    @Remark("账户解锁")
    public void unlock() {
        Ret ret = srv.unlock(getParaToInt("id"));
        renderJson(ret);
    }

    /**
     * 分配角色
     */
    @Remark("账户分配角色页面")
    public void assignRoles() {
        Account account = srv.findById(getParaToInt("id"));
        List<Role> roleList = roleAdminService.getAllRoles();
        srv.markAssignedRoles(account, roleList);

        setAttr("account", account);
        setAttr("roleList", roleList);
        render("assign_roles.html");
    }

    /**
     * 添加角色
     */
    @Remark("账户角色添加")
    public void addRole() {
        Ret ret = srv.addRole(getParaToInt("accountId"), getParaToInt("roleId"));
        renderJson(ret);
    }

    /**
     * 删除角色
     */
    @Remark("账户角色删除")
    public void deleteRole() {
        Ret ret = srv.deleteRole(getParaToInt("accountId"), getParaToInt("roleId"));
        renderJson(ret);
    }


    /**
     * 显示 "后台账户/管理员" 列表，在 account_role 表中存在的账户(被分配过角色的账户)
     * 被定义为 "后台账户/管理员"
     * <p>
     * 该功能便于查看后台都有哪些账户被分配了角色，在对账户误操作分配了角色时，也便于取消角色分配
     */
    @Remark("显示后台管理员账户首页")
    public void showAdminList() {
        List<Record> adminList = srv.getAdminList();
        setAttr("adminList", adminList);
        render("admin_list.html");
    }

    /**
     * 上传用户图片，为裁切头像做准备
     */
    @Remark("账户头像裁剪")
    public void uploadAvatar() {
        UploadFile uf = null;
        try {
            uf = getFile("avatar", srv.getAvatarTempDir(), srv.getAvatarMaxSize());
            if (uf == null) {
                renderJson(Ret.fail("msg", "请先选择上传文件"));
                return;
            }
        } catch (Exception e) {
            if (e instanceof com.jfinal.upload.ExceededSizeException) {
                renderJson(Ret.fail("msg", "文件大小超出范围"));
            } else {
                if (uf != null) {
                    // 只有出现异常时才能删除，不能在 finally 中删，因为后面需要用到上传文件
                    uf.getFile().delete();
                }
                renderJson(Ret.fail("msg", e.getMessage()));
            }
            return;
        }

        Ret ret = srv.uploadAvatar(getLoginAccountId(), uf);
        // 上传成功则将文件 url 径暂存起来，供下个环节进行裁切
        if (ret.isOk()) {
            setSessionAttr("avatarUrl", ret.get("avatarUrl"));
        }
        renderJson(ret);
    }

    /**
     * 保存 jcrop 裁切区域为用户头像
     */
    @Remark("账户头像保存")
    @Before(POST.class)
    public void saveAvatar() {
        String avatarUrl = getSessionAttr("avatarUrl");
        Integer id = getParaToInt("id");
        int x = getParaToInt("x");
        int y = getParaToInt("y");
        int width = getParaToInt("width");
        int height = getParaToInt("height");
        Ret ret = srv.saveAvatar(id == null ? getLoginAccount() : getAccount(id), avatarUrl, x, y, width, height);
        renderJson(ret);
    }

    /**
     * 删除存放在临时目录的文件
     */
    @Remark("删除存放临时目录的图片文件")
    public void delTemFile() {
        //String avatarUrl = getPara("avatarUrl");
        String avatarUrl = getSessionAttr("avatarUrl");
        String tempFile = PathKit.getWebRootPath() + avatarUrl;
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete();
            renderJson(Ret.ok().set("msg", "临时图片已经删除！"));
            return;
        }
        renderJson(Ret.fail().set("msg", "临时文件不存在，或者临时文件已经删除！"));
    }


    @Remark("账户登录日志首页")
    @ActionKey("/admin/loginLog")
    public void loginLog() {
        Page<Record> page = srv.findLoginLog(getParaToInt("p", 1), getParaToInt("size", 10));
        setAttr("page", page);
        render("login_log.html");
    }

    /**
     * 图片上传日志记录
     */
    @Remark("图片上传日志记录首页")
    @ActionKey("/admin/uploadLog")
    public void uploadLog(){
        Page<Record> page = srv.findUploadLog(getParaToInt("p", 1), getParaToInt("size", 10));
        setAttr("page", page);
        render("upload_log.html");
    }

}
