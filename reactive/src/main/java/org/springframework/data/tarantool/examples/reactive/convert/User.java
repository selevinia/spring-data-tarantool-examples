package org.springframework.data.tarantool.examples.reactive.convert;

import lombok.*;
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
@Space("users")
public class User {
    @PrimaryKey
    private UUID id;

    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    @Field("birth_date")
    private LocalDate birthDate;
    private String email;
    private Address address;
}
