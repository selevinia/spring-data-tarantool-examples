package org.springframework.data.tarantool.examples.imperative.entitycallback;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), User.class);
    }

    @Test
    void shouldSaveUserWithoutId() {
        User user = User.builder()
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .email("evans@gmail.com")
                .build();

        User saved = userRepository.save(user);
        assertThat(saved.getId()).isNotNull();
    }
}