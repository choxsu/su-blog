package com.syc.api.controller;

import com.syc.api.kit.EmailKit;
import com.syc.api.service.common.RedisService;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author choxsu
 */
@RestController
@RequestMapping(value = "/demo")
@Slf4j
public class DemoController {

    @Autowired
    private RedisService redisService;

    @Autowired
    EmailKit emailKit;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map demoTest(@RequestParam String key, @RequestParam String value) {
        boolean isSu = redisService.set(key, value);
        if (isSu) {
            return Ret.ok("msg", "插入成功");
        }
        return Ret.fail("msg", "系统错误");
    }

    @RequestMapping(value = "/getTest", method = RequestMethod.GET)
    public Map getTest(@RequestParam String key) {
        Object o = redisService.get(key);
        return Ret.ok("data", o);
    }

    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    public Map sendMail(@RequestParam("email") String email) {
        boolean b = emailKit.sendEmail(
                email,
                "标题 123 ",
                "内容 <div style='color:red;'>123</div> ");
        if (!b) {
            return Ret.fail().set("msg", "邮件发送失败");
        }
        return Ret.ok().set("msg", "发送到邮件：" + email + " 成功！");
    }

    @RequestMapping(value = "/sendHtmlMail", method = RequestMethod.GET)
    public Map sendHtmlMail(@RequestParam("email") String email) {
        boolean b = emailKit.sendHtmlEmail(
                email,
                "标题 123 ",
                "内容 <div style='color:red;'>123</div>");
        if (!b) {
            return Ret.fail().set("msg", "邮件发送失败");
        }
        return Ret.ok().set("msg", "发送到邮件：" + email + " 成功！");
    }


}
