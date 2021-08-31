package org.springframework.data.tarantool.examples.caching.customized;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.TarantoolCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.tarantool.cache.TarantoolCacheConfiguration;

import java.time.Duration;

@EnableCaching
@SpringBootApplication
public class ApplicationConfiguration {

    @Bean
    public TarantoolCacheManagerBuilderCustomizer tarantoolCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("short_term_users", TarantoolCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(1)));
    }
}
