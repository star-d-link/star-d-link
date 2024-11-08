package com.udemy.star_d_link.security.auth;

import java.security.Principal;
import lombok.RequiredArgsConstructor;

/**
 * Security Context에서 필요할 때 꺼내 사용할 수 있는 사용자의 정보를 저장하는 역할을 한다.
 */
@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal {

    private final String username;


    @Override
    public String getName() {
        return username;
    }
}
