package com.yushow.common.annotation;

import com.yushow.common.constant.RedisKeyConstant;
import com.yushow.common.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author kyj
 * @date 2022/6/8 13:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default RedisKeyConstant.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
