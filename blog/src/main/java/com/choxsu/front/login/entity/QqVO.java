package com.choxsu.front.login.entity;

import com.choxsu.config.ChoxsuConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author choxsu
 */
@Getter
@Setter
public class QqVO {

    private String appId = ChoxsuConfig.p.get("qq.appId");
    private String appKey = ChoxsuConfig.p.get("qq.appKey");
    private String redirectUri = ChoxsuConfig.p.get("qq.callback");

}
