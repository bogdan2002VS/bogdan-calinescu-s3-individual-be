package com.veganny.business.jwt;

import com.veganny.domain.AccessToken;
import com.veganny.domain.User;


public interface IAccessTokenHelper {
    String generateAccessToken(User user);

    AccessToken decode(String accessTokenEncoded);
}
