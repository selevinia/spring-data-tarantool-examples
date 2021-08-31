package org.springframework.data.tarantool.examples.caching.customized;

import lombok.*;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.PrimaryKey;
import org.springframework.data.tarantool.core.mapping.Space;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Space("users")
public class User implements Serializable {
    @PrimaryKey
    private UUID id;

    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    @Field("birth_date")
    private LocalDate birthDate;
    private String email;
}
