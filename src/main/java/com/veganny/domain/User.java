package com.veganny.domain;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private Long id;

    private IRole role;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;

}
