package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> testFilterRegistration3() {
//        FilterRegistrationBean<HiddenHttpMethodFilter> registration = new FilterRegistrationBean<HiddenHttpMethodFilter>();
//        registration.setFilter(new HiddenHttpMethodFilter());//添加过滤器
//        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//        registration.setName("HiddenHttpMethodFilter");//设置优先级
//        registration.setOrder(2);//设置优先级
//        return registration;
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 是否支持安全证书
        config.addAllowedOrigin("*"); // 允许任何域名使用
        config.addAllowedHeader("*"); // 允许任何头
        config.addAllowedMethod("*"); // 允许任何方法（post、get等）
        // 预检请求的有效期，单位为秒。
        //        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
