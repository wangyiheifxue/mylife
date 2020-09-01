package com.mylife.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @descirption : webSocket配置类
 * @author : wyh
 * @date : 2020/9/1 10:50
 */
@Configuration
public class WebSocketConfig {

    /**
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的webSocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
