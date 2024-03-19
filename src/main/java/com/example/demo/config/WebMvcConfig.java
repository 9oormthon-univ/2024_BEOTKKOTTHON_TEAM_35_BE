package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/static/", "classpath:/public/",
            "classpath:/", "classpath:/resources/" };

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "index" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter() {
        return new HeaderFilter();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 모든 경로에 대해
//                .allowedOrigins("http://172.30.1.77:3000") // 허용할 오리진 지정
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 지정
//                .allowCredentials(true) // 쿠키를 포함한 요청 허용
//                .maxAge(3600); // pre-flight 요청의 결과를 캐시하는 시간(초)
//    }
}

