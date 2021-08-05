package org.springframework.data.tarantool.examples.imperative.entitycallback;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.tarantool.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

@SpringBootApplication
public class ApplicationConfiguration {

    @Bean
    public BeforeConvertCallback<User> beforeConvertCallback() {
        return (user, spaceName) -> {
            if (user.getId() == null) {
                user.setId(UUID.randomUUID());
                return user;
            }
            return user;
        };
    }
}
