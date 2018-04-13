package com.choxsu.elastic.config;

/**
 * @author choxsu
 */

import java.lang.annotation.*;

/**
 * Jfinal事物交给spring管理注解
 * 目前只支持用在方法上
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JFinalTx {

}
