

package com.choxsu._admin.account;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Role;
import com.choxsu.common.entity.Session;
import com.choxsu.login.LoginService;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 账户管理
 */
public class AccountAdminService {

	@Inject
	LoginService loginService;

	private Account dao = new Account().dao();

	public Page<Account> paginate(int pageNum) {
		return dao.paginate(pageNum, 10, "select *", "from account order by id desc");
	}

	public Account findById(int accountId) {
		return dao.findById(accountId);
	}

	/**
	 * 注意要验证 nickName 与 userName 是否存在
	 */
	public Ret update(Account account) {
		String nickName = account.getNickName().toLowerCase().trim();
		String sql = "select id from account where lower(nickName) = ? and id != ? limit 1";
		Integer id = Db.queryInt(sql, nickName, account.getId());
		if (id != null) {
			return Ret.fail("msg", "昵称已经存在，请输入别的昵称");
		}

		String userName = account.getUserName().toLowerCase().trim();
		sql = "select id from account where lower(userName) = ? and id != ? limit 1";
		id = Db.queryInt(sql, userName, account.getId());
		if (id != null) {
			return Ret.fail("msg", "邮箱已经存在，请输入别的昵称");
		}

		// 暂时只允许修改 nickName 与 userName
		account.keep("id", "nickName", "userName");
		account.update();
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
			for (Session session : sessionList) {			// 处理多客户端同时登录后的多 session 记录
				loginService.logout(session.getId());    // 清除登录 cache，强制退出
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
		int n = Db.update("update account set status = ? where status != ? and id = ?", Account.STATUS_OK , Account.STATUS_REG , accountId);
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
}
