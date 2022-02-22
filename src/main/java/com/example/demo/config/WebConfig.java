package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李昕
 * @date 2022/2/20 18:06
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                //该字段是必须的，用来列出浏览器的CORS请求会用到哪些HTTP方法，
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                //该字段是一个逗号分隔的字符串，指定浏览器CORS请求会额外发送的头信息字段，上例是X-Custom-Header。
                .allowedHeaders("Access-Control-Allow-Origin","token","secret")
                //CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。
                //.exposedHeaders("token","secret")
                .allowCredentials(true)//它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中
                //该字段可选，用来指定本次预检请求的有效期，单位为秒
                .maxAge(3600);
    }
}
