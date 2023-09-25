package com.icchance.q91.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 自定義 用戶認證註解
 * </p>
 * @author 6687353
 * @since 2023/9/25 11:45:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserCertificateAnnotation {

    boolean required() default true;
}
