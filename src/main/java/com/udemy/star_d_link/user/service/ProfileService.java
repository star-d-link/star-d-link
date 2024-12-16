package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.ProfileRequestDto;
import com.udemy.star_d_link.user.dto.ProfileResponseDto;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.exception.UserExceptions;
import com.udemy.star_d_link.user.repository.UserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public UserEntity updateProfile(ProfileRequestDto requestDto, String username)
        throws IOException {
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(UserExceptions.NOT_FOUND::get);

        MultipartFile profileImage = requestDto.getProfileImage();

        if (profileImage != null && !profileImage.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

//            String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID();
            Path destination = uploadPath.resolve(fileName);

            profileImage.transferTo(destination.toFile());
            userEntity.changeProfileUrl("/uploads/" + fileName);
        }

        userEntity.updateProfile(requestDto);
        return userEntity;
    }

    public ProfileResponseDto getUserProfile(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(UserExceptions.NOT_FOUND::get);

        return new ProfileResponseDto(userEntity);
    }
}
