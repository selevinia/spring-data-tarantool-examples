package org.springframework.data.tarantool.examples.caching.simple;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
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
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
    }

    @Test
    void shouldCreateAndGetUserFromCache() {
        User created = userService.createUser(user());

        userRepository.delete(created);
        assertThat(userRepository.findById(created.getId())).isEmpty();

        User cached = userService.getUser(created.getId());
        assertThat(cached).isEqualTo(created);
    }

    @Test
    void shouldCreateAndDeleteAndGetEmptyUserFromCache() {
        User created = userService.createUser(user());
        userService.deleteUser(created);

        User cached = userService.getUser(created.getId());
        assertThat(cached).isNull();
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
