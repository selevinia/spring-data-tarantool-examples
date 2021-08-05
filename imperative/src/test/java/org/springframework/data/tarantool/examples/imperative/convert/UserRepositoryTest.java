package org.springframework.data.tarantool.examples.imperative.convert;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDate;
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
    void shouldSaveAndFindUserWithAddress() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .address(Address.builder()
                        .city("Paris")
                        .street("33 Rue de Tlemcen")
                        .postcode("75020")
                        .build())
                .build();

        User saved = userRepository.save(user);
        assertThat(saved).isEqualTo(user);

        User found = userRepository.findById(user.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(user.getId());
        assertThat(found.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    void shouldSaveAndFindUserWithoutAddress() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .build();

        User saved = userRepository.save(user);
        assertThat(saved).isEqualTo(user);

        User found = userRepository.findById(user.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(user.getId());
        assertThat(found.getAddress()).isNull();
    }
}