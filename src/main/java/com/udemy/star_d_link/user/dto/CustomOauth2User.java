package com.udemy.star_d_link.user.dto;

import com.udemy.star_d_link.user.entity.UserEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOauth2User implements OAuth2User {

    private final UserDTO userEntity;

    public CustomOauth2User(UserDTO userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return collection;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return userEntity.getNickname();
    }

    public String getUsername() {
        return userEntity.getUsername();
    }
}
