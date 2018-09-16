package com.syc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author chox su
 * @date 2018/01/17 18:04
 */
@SpringBootApplication
@EnableCaching
public class MorningApplication {
    public static void main(String[] args) {
        SpringApplication.run(MorningApplication.class, args);
    }
}
