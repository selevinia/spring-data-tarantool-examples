package org.springframework.data.tarantool.examples.caching.simple;

import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.UUID;

public interface UserRepository extends TarantoolRepository<User, UUID> {
}
