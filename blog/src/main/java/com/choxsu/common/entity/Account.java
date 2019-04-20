package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseAccount;
import com.choxsu.common.safe.JsoupFilter;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class Account extends BaseAccount<Account> {

    public static final String AVATAR_NO_AVATAR = "default.png";    // 刚注册时使用默认头像

    public static final int STATUS_LOCK_ID = -1;    // 锁定账号，无法做任何事情
    public static final int STATUS_REG = 0;            // 注册、未激活
    public static final int STATUS_OK = 1;            // 正常、已激活
    public static final int IS_THIRD_LOGIN = 1;     // 第三方登陆
    public static final int NOT_THIRD_LOGIN = 0;    // 非第三方登陆

    public boolean isStatusOk() {
        return getStatus() == STATUS_OK;
    }

    public boolean isStatusReg() {
        return getStatus() == STATUS_REG;
    }

    public boolean isStatusLockId() {
        return getStatus() == STATUS_LOCK_ID;
    }

    /**
     * 过滤掉 nickName 中的 html 标记，恶意脚本
     */
    protected void filter(int filterBy) {
        JsoupFilter.filterAccountNickName(this);
    }

    public Account removeSensitiveInfo() {
        remove("password", "salt");
        return this;
    }


}
