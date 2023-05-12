package com.veganny.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserReq {

    @Length(min = 2, max = 50)
    private String username;


    @Length(min = 3, max = 50)
    private String password;


    @Length(min = 2, max = 50)
    private String firstName;


    @Length(min = 2, max = 50)
    private String lastName;


    @Length(min = 5, max = 50)
    private String email;


    @Length(min = 2, max = 50)
    private String address;

    @Length(min = 8, max = 50)
    private String phone;
}
