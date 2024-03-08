package dev.luisjohann.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SingletonConfiguration {

    @Bean
    @Scope("singleton")
    public JooqContext jooqContext() {
        return new JooqContext();
    }
}
