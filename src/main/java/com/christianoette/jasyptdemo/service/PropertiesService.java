package com.christianoette.jasyptdemo.service;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class PropertiesService {

    @Value("${token}")
    private String token;

    @Value("${password}")
    private String password;

    @EventListener(ApplicationStartedEvent.class)
    public void logProperties(ApplicationStartedEvent event) {
        logEnvironmentProperties(event.getApplicationContext().getEnvironment());
    }

    private void logEnvironmentProperties(final Environment env) {
        System.out.println("====== Environment and configuration ======");
        System.out.println("Active profiles: " + Arrays.toString(env.getActiveProfiles()));
        final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false)
            .filter(ps -> ps instanceof EnumerablePropertySource)
            .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
            .flatMap(Arrays::stream)
            .distinct()
            .forEach(prop -> System.out.println(prop + ": " + getProperty(env, prop)));
        System.out.println("===========================================");
    }

    private String getProperty(final Environment env, final String prop) {
        try {
            return env.getProperty(prop);
        } catch (Exception ex) {
            return "NOT_FOUND_IN_ENVIRONMENT";
        }
    }
}
