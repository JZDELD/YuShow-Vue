package com.yushow.framework.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JacksonConfig
 *
 * @author kyj
 * @date 2022/6/14 17:25
 */
@Configuration
public class JacksonConfig {
    /**
     * 解决json时间类型转换失败问题
     *
     * @return
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }
}
