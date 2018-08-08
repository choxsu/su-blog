package com.choxsu.suyuchen.controller;

import com.choxsu.suyuchen.service.common.RedisService;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map demoTest(String key, String value) {
        boolean isSu = redisService.set(key, value);
        if (isSu) {
            return Ret.ok("msg", "插入成功");
        }
        return Ret.fail("msg", "系统错误");
    }

    @RequestMapping(value = "/getTest", method = RequestMethod.GET)
    public Map getTest(String key) {
        Object o = redisService.get(key);
        return Ret.ok("data", o);
    }

}
