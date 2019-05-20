package com.choxsu._front.login.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choxsu._front.login.entity.QQVo;
import com.choxsu._front.login.entity.Token;
import com.choxsu._front.login.entity.QQUserInfo;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;

/**
 * @author choxsu
 */
public class QQKit {

    public static String getToken(String code, QQVo qqVo) {
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=authorization_code");
        url.append("&client_id=").append(qqVo.getAppId());
        url.append("&client_secret=").append(qqVo.getAppKey());
        url.append("&code=").append(code);
        //回调地址
        String redirect_uri = qqVo.getRedirectUri();
        //转码
        url.append("&redirect_uri=").append(redirect_uri);
        return HttpKit.get(url.toString());
    }

    /**
     * callback( {"client_id":"101568421","openid":"C561FD0C7A0E9B3810F38AE1A12A978D"} );
     * @param accessToken
     * @return
     */
    public static String getOpenId(String accessToken) {
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
        //获取保存的用户的token
        if (StrKit.isBlank(accessToken)){
            return null;
        }
        url.append("access_token=").append(accessToken);
        String result = HttpKit.get(url.toString());
        int startIndex = result.indexOf("(");
        int endIndex = result.lastIndexOf(")");
        String json = result.substring(startIndex+1, endIndex);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }

    /**
     * "access_token=60951872E5B815BF8F12859C4334D2E7&expires_in=7776000&refresh_token=D6B33F16BBF09BA32D7B770E7C59AC4C"
     * @param result
     * @return
     */
    public static Token tokenHandler(String result) {
        if (StrKit.isBlank(result)) {
            return null;
        }
        String[] split = result.split("&");
        Token token = new Token();
        for (String s : split) {
            String[] kv = s.split("=");
            switch (kv[0]) {
                case "access_token":
                    token.setAccessToken(kv[1]);
                    break;
                case "expires_in":
                    token.setExpiresIn(Integer.parseInt(kv[1]));
                    break;
                case "refresh_token":
                    token.setRefreshToken(kv[1]);
            }
        }
        return token;
    }


    /**
     * 获取用户信息
     * @return
     * @throws Exception
     */
    public static QQUserInfo getUserInfo(String accessToken, String openId, String appId) {
        StringBuilder url = new StringBuilder("https://graph.qq.com/user/get_user_info?");
        if (StrKit.isBlank(accessToken) || StrKit.isBlank(openId)){
            return null;
        }
        url.append("access_token=").append(accessToken);
        url.append("&oauth_consumer_key=").append(appId);
        url.append("&openid=").append(openId);
        String result = HttpKit.get(url.toString());
        return JSON.parseObject(result,QQUserInfo.class);
    }

    public static void main(String[] args) {
        String s = "callback( {\"client_id\":\"101568421\",\"openid\":\"C561FD0C7A0E9B3810F38AE1A12A978D\"} );";
        int startIndex = s.indexOf("(");
        int endIndex = s.lastIndexOf(")");
        String json = s.substring(startIndex+1, endIndex);
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject.getString("openid"));

    }

    public static String getAuthUrl(QQVo qqVo) {
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/authorize?");
        url.append("response_type=code");
        url.append("&client_id=").append(qqVo.getAppId());
        //回调地址 ,回调地址要进行Encode转码
        String redirect_uri = qqVo.getRedirectUri();
        //转码
        url.append("&redirect_uri=").append(redirect_uri);
        url.append("&state=ok");
        return url.toString();
    }
}
