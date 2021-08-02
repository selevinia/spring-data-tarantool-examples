package org.springframework.data.tarantool.examples.reactive.auditing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.tarantool.config.EnableReactiveTarantoolAuditing;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableReactiveTarantoolAuditing
public class ApplicationConfiguration {

    @Bean
    public ReactiveAuditorAware<String> reactiveAuditorAware() {
        return () -> Mono.just("the-current-user");
    }
}
