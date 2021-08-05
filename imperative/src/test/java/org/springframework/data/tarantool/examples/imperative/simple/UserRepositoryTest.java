package org.springframework.data.tarantool.examples.imperative.simple;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    void shouldSaveAndFindUser() {
        User user = user();
        User saved = userRepository.save(user);
        assertThat(saved).isEqualTo(user);

        User found = userRepository.findById(user.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found).isEqualTo(user);
    }

    @Test
    void shouldCountUsersByLastName() {
        userRepository.saveAll(List.of(user(), user(), user()));

        Long count = userRepository.countUsersByLastName("Evans");
        assertThat(count).isEqualTo(3L);
    }

    @Test
    void shouldFindUsersByLastName() {
        userRepository.saveAll(List.of(user(), user(), user()));

        List<User> users = userRepository.findAllUsersByLastName("Evans");
        assertThat(users).hasSize(3);
    }

    @Test
    void shouldFindUsersByFirstNameAndLastName() {
        User user = user();
        userRepository.save(user);

        List<User> users = userRepository.findAllByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    void shouldFindUsersByBirthDate() {
        User user = user();
        userRepository.save(user);

        List<User> users = userRepository.findAllByBirthDate(user.getBirthDate());
        assertThat(users).hasSize(1);
        assertThat(users.get(0)).isEqualTo(user);
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