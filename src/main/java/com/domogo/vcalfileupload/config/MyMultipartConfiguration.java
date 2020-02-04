package com.domogo.vcalfileupload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@ConfigurationProperties
public class MyMultipartConfiguration {

    @Bean(name = "customMultipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(52428800);
        return multipartResolver;
    }

}