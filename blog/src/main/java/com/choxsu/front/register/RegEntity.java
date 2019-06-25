package com.choxsu.front.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author choxsu
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegEntity {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String passwordAgain;

}
