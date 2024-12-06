package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.JoinDTO;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.entity.UserEntity;

public interface UserService {

    UserEntity saveUser(JoinDTO user);

    UserDTO read(String username, String password);

    UserDTO getByUsername(String username);
}
