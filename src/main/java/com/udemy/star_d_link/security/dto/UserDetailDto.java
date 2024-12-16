package com.udemy.star_d_link.security.dto;

import com.udemy.star_d_link.user.dto.UserDTO;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security에서 사용되는 UserDetails 인터페이스를 정의한 DTO 클래스
 */
@Slf4j
public class UserDetailDto implements UserDetails {

//    @Delegate
    private UserDTO userDTO;

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * @return
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
