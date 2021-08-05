package org.springframework.data.tarantool.examples.imperative.auditing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.tarantool.config.EnableTarantoolAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableTarantoolAuditing
public class ApplicationConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("the-current-user");
    }
}
