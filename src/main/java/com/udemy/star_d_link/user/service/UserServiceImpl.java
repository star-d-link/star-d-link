package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.JoinDTO;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.exception.UserExceptions;
import com.udemy.star_d_link.user.repository.UserRepository;
import com.udemy.star_d_link.user.util.RandomNickname;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomNickname randomNickname;


    /**
     * @param user
     * @return
     */
    @Override
    public UserEntity saveUser(JoinDTO user) {
        String username = user.getUsername();
        String email = user.getEmail();

        if (userRepository.existsByUsername(username)) {
            throw UserExceptions.DUPLICATE_NICKNAME.get();
        }
        if (userRepository.existsByEmail(email)) {
            throw UserExceptions.DUPLICATE_EMAIL.get();
        }
        user.setNickname(randomNickname.generate());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user.toEntity());
    }

    @Override
    public UserDTO read(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(UserExceptions.BAD_CREDENTIALS::get);

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw UserExceptions.BAD_CREDENTIALS.get();
        }

        return new UserDTO(userEntity);
    }

    @Override
    public UserDTO getByUsername(String username) {
        Optional<UserEntity> result = userRepository.findByUsername(username);

        UserEntity userEntity = result.orElseThrow(UserExceptions.NOT_FOUND::get);

        return new UserDTO(userEntity);
    }

}
