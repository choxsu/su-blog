package com.choxsu._front.login.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author choxsu
 */
@Getter
@Setter
@ToString
public class Token {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
}
