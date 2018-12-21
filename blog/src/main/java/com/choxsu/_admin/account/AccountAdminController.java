
package com.choxsu._admin.account;

import com.choxsu._admin.role.RoleAdminService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Role;
import com.choxsu.kit.IpKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 账户管理控制器
 */
public class AccountAdminController extends BaseController {

    @Inject
    AccountAdminService srv;
    @Inject
    RoleAdminService roleAdminService;

    public void index() {
        Page<Account> accountPage = srv.paginate(getParaToInt("p", 1));
        setAttr("accountPage", accountPage);
        render("index.html");
    }


    public void add() {
        keepPara("p");
        render("add.html");
    }

    @Before(AccountSaveUpdateValidator.class)
    public void save() {
        Account account = getBean(Account.class);
        Ret ret = srv.save(account.getUserName(), account.getPassword(), account.getNickName(), IpKit.getRealIp(getRequest()));
        renderJson(ret);
    }

    public void del() {
        Ret ret = srv.delete(getParaToInt("id"));
        renderJson(ret);
    }


    public void edit() {
        keepPara("p");
        Account account = srv.findById(getParaToInt("id"));
        setAttr("account", account);
        render("edit.html");
    }

    /**
     * 提交修改
     */
    @Before(AccountSaveUpdateValidator.class)
    public void update() {
        Account account = getBean(Account.class);
        Ret ret = srv.update(account);
        renderJson(ret);
    }


    /**
     * 账户锁定
     */
    public void lock() {
        Ret ret = srv.lock(getLoginAccountId(), getParaToInt("id"));
        renderJson(ret);
    }

    /**
     * 账户解锁
     */
    public void unlock() {
        Ret ret = srv.unlock(getParaToInt("id"));
        renderJson(ret);
    }

    /**
     * 分配角色
     */
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
    public void addRole() {
        Ret ret = srv.addRole(getParaToInt("accountId"), getParaToInt("roleId"));
        renderJson(ret);
    }

    /**
     * 删除角色
     */
    public void deleteRole() {
        Ret ret = srv.deleteRole(getParaToInt("accountId"), getParaToInt("roleId"));
        renderJson(ret);
    }


    /**
     * 显示 "后台账户/管理员" 列表，在 account_role 表中存在的账户(被分配过角色的账户)
     * 被定义为 "后台账户/管理员"
     *
     * 该功能便于查看后台都有哪些账户被分配了角色，在对账户误操作分配了角色时，也便于取消角色分配
     */
    public void showAdminList() {
        List<Record> adminList = srv.getAdminList();
        setAttr("adminList", adminList);
        render("admin_list.html");
    }

}
