package com.choxsu.front.login.entity;

import com.choxsu.ChoxsuApplication;
import lombok.Getter;
import lombok.Setter;

/**
 * @author choxsu
 */
@Getter
@Setter
public class QQVo {

    private String appId = ChoxsuApplication.p.get("qq.appId");
    private String appKey = ChoxsuApplication.p.get("qq.appKey");
    private String redirectUri = ChoxsuApplication.p.get("qq.callback");

}
