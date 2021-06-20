package com.ssb.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.ssb.dev")
public class SsbDevAssignmentApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SsbDevAssignmentApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(SsbDevAssignmentApplication.class, args);
    }

}
