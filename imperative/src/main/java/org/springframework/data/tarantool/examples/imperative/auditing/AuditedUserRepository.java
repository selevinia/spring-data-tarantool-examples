package org.springframework.data.tarantool.examples.imperative.auditing;

import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.UUID;

public interface AuditedUserRepository extends TarantoolRepository<AuditedUser, UUID> {
}
