package org.springframework.data.tarantool.examples.reactive.auditing;

import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;

import java.util.UUID;

public interface AuditedUserRepository extends ReactiveTarantoolRepository<AuditedUser, UUID> {
}
