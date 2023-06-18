package com.veganny.configuration.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PasswordEncoderConfig.class)
 class PasswordEncoderConfigTest {

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Test
    @DisplayName("Should create a BCryptPasswordEncoder instance")
    void createBCryptPasswordEncoderInstance() {
        BCryptPasswordEncoder bCryptPasswordEncoder = (BCryptPasswordEncoder) passwordEncoderConfig.createBCryptPasswordEncoder();
        assertNotNull(bCryptPasswordEncoder);
        assertTrue(bCryptPasswordEncoder instanceof BCryptPasswordEncoder);
    }

}