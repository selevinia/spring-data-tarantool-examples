package org.springframework.data.tarantool.examples.imperative.auditing;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.PrimaryKey;
import org.springframework.data.tarantool.core.mapping.Space;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Space("audited_users")
public class AuditedUser implements Persistable<UUID> {
    @PrimaryKey
    private UUID id;

    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;

    @CreatedBy
    @Field("created_by")
    private String createdBy;
    @CreatedDate
    @Field("created_date")
    private LocalDateTime createdDate;
    @LastModifiedBy
    @Field("last_modified_by")
    private String lastModifiedBy;
    @LastModifiedDate
    @Field("last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Transient
    private boolean isNew;
}
