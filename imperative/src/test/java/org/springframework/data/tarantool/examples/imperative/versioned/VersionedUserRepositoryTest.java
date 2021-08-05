package org.springframework.data.tarantool.examples.imperative.versioned;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class VersionedUserRepositoryTest {

    @Autowired
    private VersionedUserRepository userRepository;

    @Autowired
    private TarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), VersionedUser.class);
    }

    @Test
    void shouldInsertUser() {
        VersionedUser user = user();

        VersionedUser saved = userRepository.save(user);
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getVersion()).isEqualTo(0);
    }

    @Test
    void shouldUpdateUser() {
        VersionedUser user = user();
        userRepository.save(user);

        VersionedUser updated = user();
        updated.setId(user.getId());
        updated.setFirstName(user.getLastName());
        VersionedUser saved = userRepository.save(user);
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getVersion()).isEqualTo(1);
    }

    private VersionedUser user() {
        return VersionedUser.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .build();
    }
}