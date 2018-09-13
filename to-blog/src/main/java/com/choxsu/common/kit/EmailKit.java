

package com.choxsu.common.kit;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

/**
 * 邮件发送工具类
 */
public class EmailKit {

    private static final Log log = Log.getLog(EmailKit.class);

    public static String sendEmail(String fromEmail, String toEmail, String title, String content, boolean isSendMsg) {
        return sendEmail(null, fromEmail, null, toEmail, title, content, isSendMsg);
    }

    /**
     * @param emailServer
     * @param fromEmail
     * @param password
     * @param toEmail
     * @param title
     * @param content
     * @param isSendMsg
     * @return
     */
    public static String sendEmail(String emailServer,
                                   String fromEmail,
                                   String password,
                                   String toEmail,
                                   String title,
                                   String content,
                                   boolean isSendMsg) {

        Email email = isSendMsg ? new SimpleEmail() : new HtmlEmail();
        if (StrKit.notBlank(emailServer)) {
            email.setHostName(emailServer);
        } else {
            // 默认使用本地 postfix 发送，这样就可以将postfix 的 mynetworks 配置为 127.0.0.1 或 127.0.0.0/8 了
            email.setHostName("127.0.0.1");
        }

        // 如果密码为空，则不进行认证
        if (StrKit.notBlank(password)) {
            email.setAuthentication(fromEmail, password);
        }

        email.setCharset("utf-8");
        try {
            email.addTo(toEmail);
            email.setFrom(fromEmail);
            email.setSubject(title);
            email.setMsg(content);
            email.setSentDate(new Date());
            return email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            EmailKit.sendEmail("smtp.163.com", "from@163.com", "------", "to@qq.com",
                    "标题，测试" + new Date(),
                    "内容，测试<div>123</div>" + new Date(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("邮件发送成功！");
    }

}

		
	
	


