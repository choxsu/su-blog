package com.choxsu._front.register;

import com.choxsu._admin.account.AccountAdminService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;

public class RegisterService {

    @Inject
    AccountAdminService accountAdminService;

    public Ret register(RegEntity regModel, String ip) {
        String userName = regModel.getUserName();
        String nickName = regModel.getNickname();
        String password = regModel.getPassword();
        return accountAdminService.save(userName, password, nickName, ip, true);
    }
}
