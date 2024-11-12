package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.UserDTO;

public interface UserService {

    UserDTO read(String username, String password);

    UserDTO getByUsername(String username);
}
