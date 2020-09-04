package com.mylife.conf;

import com.mylife.filter.RequestParamFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @descirption : 自定义过滤器注册类
 * @author : wyh
 * @date : 2020/9/4 11:44
 */
@Configuration
public class FilterConfig {

    /**
     * 自定义过滤器注册
     * @return
     */
	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		// 请求参数过滤器
		registration.setFilter(new RequestParamFilter());
		registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MAX_VALUE);
		registration.setName("requestParamFilter");
		return registration;
	}

}
