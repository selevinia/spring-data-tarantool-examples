package org.springframework.data.tarantool.examples.caching.customized;

import io.tarantool.driver.api.conditions.Conditions;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TarantoolOperations operations;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), User.class);
        Objects.requireNonNull(cacheManager.getCache("short_term_users")).clear();
        Objects.requireNonNull(cacheManager.getCache("long_term_users")).clear();
    }

    @Test
    @SneakyThrows
    void shouldGetUsersFromCache() {
        User created = userRepository.save(user());

        assertThat(userService.getShortTermUser(created.getId())).isEqualTo(created);
        assertThat(userService.getLongTermUser(created.getId())).isEqualTo(created);

        Thread.sleep(2000);

        userRepository.delete(created);

        assertThat(userService.getShortTermUser(created.getId())).isNull();
        assertThat(userService.getLongTermUser(created.getId())).isEqualTo(created);
    }

    private User user() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .email("evans@gmail.com")
                .build();
    }
}
