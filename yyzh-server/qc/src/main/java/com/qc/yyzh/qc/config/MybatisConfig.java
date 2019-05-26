package com.qc.yyzh.qc.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qc
 * @date 2019/4/26
 */
@Configuration
public class MybatisConfig {
    @Bean
    ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(false);
            }

        };
    }
}
