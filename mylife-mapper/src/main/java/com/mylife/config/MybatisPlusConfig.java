package com.mylife.config;

import com.mylife.config.mysqlInjector.base.MySqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mylife.mapper")
public class MybatisPlusConfig {

    /**
     * 自定义Sql注入
     * @return
     */
    @Bean
    public MySqlInjector sqlInjector(){
        return new MySqlInjector();
    }
}
