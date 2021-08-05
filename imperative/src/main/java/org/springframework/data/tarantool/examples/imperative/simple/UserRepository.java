package org.springframework.data.tarantool.examples.imperative.simple;

import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends TarantoolRepository<User, UUID> {

    List<User> findAllByBirthDate(LocalDate birthDate);

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);

    @Query(function = "find_users_by_last_name")
    List<User> findAllUsersByLastName(String lastName);

    @Query(function = "count_users_by_last_name")
    Long countUsersByLastName(String lastName);
}
