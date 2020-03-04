package com.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//解决跨域问题
@Configuration
public class Corsfiguration {

    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许跨域的域名，"*"为所有，但不能带cookie
//        corsConfiguration.addAllowedOrigin("http://127.0.0.1:9003");nginx反向代理的端口
//        corsConfiguration.addAllowedOrigin("http://localhost:9003");
        corsConfiguration.addAllowedOrigin("http://www.origami.com");
        corsConfiguration.addAllowedOrigin("http://api.origami.com");
        corsConfiguration.setAllowCredentials(true);//是否携带cookie
        corsConfiguration.addAllowedMethod("*");//代表所有请求方法 get post delete put....
        corsConfiguration.addAllowedHeader("Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//是否允许携带任何头信息
        corsConfiguration.addAllowedHeader("*");//是否允许携带任何头信息

        //出书画cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);

        //返回corsFilter实例，参数：cors配置源对象
        return new CorsFilter(configurationSource);
    }

}
