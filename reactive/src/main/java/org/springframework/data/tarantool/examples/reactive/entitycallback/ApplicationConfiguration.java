package org.springframework.data.tarantool.examples.reactive.entitycallback;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.tarantool.core.mapping.event.ReactiveBeforeConvertCallback;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootApplication
public class ApplicationConfiguration {

    @Bean
    public ReactiveBeforeConvertCallback<User> beforeConvertCallback() {
        return (user, spaceName) -> {
            if (user.getId() == null) {
                return Mono.just(user)
                        .map(u -> {
                            u.setId(UUID.randomUUID());
                            return u;
                        });
            }
            return Mono.just(user);
        };
    }
}
