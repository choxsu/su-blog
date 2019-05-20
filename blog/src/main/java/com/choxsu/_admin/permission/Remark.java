package com.choxsu._admin.permission;

import java.lang.annotation.*;

/**
 * 权限一键同步功能时，自动向 permission 表的 remark 字段中
 * 添加 @Remark 注解的内容
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Remark {
	String value();
}