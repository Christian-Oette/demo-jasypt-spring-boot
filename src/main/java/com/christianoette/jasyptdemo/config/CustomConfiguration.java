package com.christianoette.jasyptdemo.config;

import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("custom")
public class CustomConfiguration {

    private final Logger logger = LoggerFactory.getLogger(CustomConfiguration.class);

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        logger.info("Use custom configuration");
    }

    @Bean
    public StringEncryptor jasyptStringEncryptor() {
        return new StringEncryptor() {
            @Override
            public String encrypt(final String input) {
                return "s"+"-encrypted";
            }

            @Override
            public String decrypt(final String input) {
                return input.replaceAll("-encrypted", "");
            }
        };
    }
}
