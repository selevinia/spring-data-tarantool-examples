package org.springframework.data.tarantool.examples.caching.customized;

import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.UUID;

public interface UserRepository extends TarantoolRepository<User, UUID> {
}
