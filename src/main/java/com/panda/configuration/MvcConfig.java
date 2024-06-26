package com.panda.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("lo");
        registry.addViewController("/lo").setViewName("lo");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/tasks").setViewName("tasks");
        registry.addViewController("/tasksAdmin").setViewName("tasksAdmin");
        registry.addViewController("/denied").setViewName("denied");
        registry.addViewController("/task").setViewName("task");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
