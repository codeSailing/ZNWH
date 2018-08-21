/* 
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：WebConfig.java
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	  wangt   2016年10月13日下午12:52:31	       Create	
 */
package com.quickdone.znwh;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web访问控制的配置
 * 
 * @author wangt
 *
 * @version 1.0
 */
@Configuration
public class WebConfig {

	/**
	 * @description 注册filter，添加对DELETE和PUT等请求的访问控制
	 * @author wangt
	 * @create 2016年11月9日下午4:26:26
	 * @version 1.0
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean registFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new Filter() {
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request,
					ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				HttpServletResponse res = (HttpServletResponse) response;
				res.setHeader("Access-Control-Allow-Origin", "*");
				res.setHeader("Access-Control-Allow-Methods",
						"GET, POST, DELETE, PUT, OPTIONS");
				res.setHeader("Access-Control-Allow-Credentials", "false");
				res.setHeader("Access-Control-Max-Age", "3600");
				res.setHeader(
						"Access-Control-Allow-Headers",
						"Cache-Control,Pragma,Expires,accept,x-requested-with,content-type,Authorization,token");

				chain.doFilter(request, res);
			}

			@Override
			public void destroy() {
			}

		});
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		return registration;
	}
	
	/**
     * @description 设置文件上传大小限制为10M
     * @author wangt
     * @create 2016年11月11日下午5:34:06
     * @version 1.0
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10 * 1024 * 1024l);
        return factory.createMultipartConfig();
    }

}
