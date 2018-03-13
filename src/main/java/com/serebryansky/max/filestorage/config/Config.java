package com.serebryansky.max.filestorage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    public Object simpleBean() {
        log.error("Logging level is error");
        log.warn("Logging level is warn");
        log.info("Logging level is info");
        log.debug("Logging level is debug");
        log.trace("Logging level is trace");
        return new Object();
    }
}
