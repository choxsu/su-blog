

package com.choxsu.common.kit;

import com.jfinal.kit.PropKit;
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

    /**
     * @param toEmail
     * @param title
     * @param content
     * @param html
     * @return
     */
    public static String sendEmail(String toEmail,
                                   String title,
                                   String content,
                                   boolean html) {
        String emailServer = PropKit.get("emailServer");
        String username = PropKit.get("username");
        String password = PropKit.get("emailPass");
        Email email = html ? new HtmlEmail() : new SimpleEmail();
        if (StrKit.notBlank(emailServer)) {
            email.setHostName(emailServer);
        } else {
            // 默认使用本地 postfix 发送，这样就可以将postfix 的 mynetworks 配置为 127.0.0.1 或 127.0.0.0/8 了
            email.setHostName("127.0.0.1");
        }

        // 如果密码为空，则不进行认证
        if (StrKit.notBlank(password)) {
            email.setAuthentication(username, password);
        }

        email.setCharset("utf-8");
        try {
            email.setFrom(username);
            email.addTo(toEmail);
            email.setSubject(title);
            email.setMsg(content);
            email.setSentDate(new Date());
            return email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static final Log log = Log.getLog(EmailKit.class);

}

		
	
	


