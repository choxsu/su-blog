package com.choxsu.elastic.controller;

import com.choxsu.elastic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author choxsu
 */
@RestController
@RequestMapping(value = {"/test/v1"})
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping(value = "/testTran")
    public Object testTran(){


        return testService.testTran();
    }
}
