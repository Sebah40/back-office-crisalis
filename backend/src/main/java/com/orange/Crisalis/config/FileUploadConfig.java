package com.orange.Crisalis.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

@Configuration
public class FileUploadConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setResolveLazily(true);
        resolver.setMaxUploadSizePerFile(2 * 1024 * 1024); // Tamaño máximo de archivo en bytes
        resolver.setMaxInMemorySize(4096); // Tamaño máximo en memoria en bytes
        resolver.setUploadTempDir(new FileSystemResource(uploadDir));
        return resolver;
    }
}