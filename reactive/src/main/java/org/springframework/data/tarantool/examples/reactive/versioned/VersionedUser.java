package org.springframework.data.tarantool.examples.reactive.versioned;

import lombok.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.PrimaryKey;
import org.springframework.data.tarantool.core.mapping.Space;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Space("versioned_users")
public class VersionedUser {
    @PrimaryKey
    private UUID id;

    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;

    @Version
    private Long version;
}
