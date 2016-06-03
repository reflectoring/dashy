package com.github.reflectoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.DispatcherType;

@SpringBootApplication
public class Quartermaster {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Quartermaster.class).run(args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new UrlRewriteFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        // when activated add condition to urlrewrite.xml
        // <condition type="request-uri" operator="notequal">/rewrite-status/**</condition>
        registrationBean.addInitParameter("statusEnabled", "false");
        registrationBean.addInitParameter("confPath", "urlrewrite.xml");
        // registrationBean.addInitParameter("logLevel", "DEBUG");

        return registrationBean;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/*")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
}
