package com.udemy.star_d_link.security.service;

import com.udemy.star_d_link.security.dto.GoogleResponse;
import com.udemy.star_d_link.security.dto.NaverResponse;
import com.udemy.star_d_link.security.dto.Oauth2Response;
import com.udemy.star_d_link.user.constants.UserRoles;
import com.udemy.star_d_link.user.dto.CustomOauth2User;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.repository.UserRepository;
import com.udemy.star_d_link.user.util.RandomNickname;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final RandomNickname randomNickname;
    private final UserRepository userRepository;

    /**
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Oauth2Response oauth2Response = null;
        if (registrationId.equals("naver")) {
            oauth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oauth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        }

        String username = oauth2Response.getProvider() + "_" + oauth2Response.getProviderId();

        UserEntity existUser = userRepository.findByUsername(username).orElse(null);

        if (existUser == null) {
            // 해당 방법보다 사용자에게 재미를 주기 위해 RandomNickname을 사용했다.
//        String nickname = UUID.randomUUID() + "_" + oauth2Response.getName() + "_"
//            + oauth2Response.getProviderId();

            String nickname = randomNickname.generate();
            UserEntity userEntity = UserEntity.builder()
                .username(username)
                .nickname(nickname)
                .password(UUID.randomUUID().toString())
                .email(oauth2Response.getEmail())
                .role(UserRoles.ROLE_USER.name())
                .build();

            userRepository.save(userEntity);

            UserDTO userDTO = new UserDTO(userEntity);
            return new CustomOauth2User(userDTO);

        } else {
            existUser.changeEmail(oauth2Response.getEmail());

            userRepository.save(existUser);

            UserDTO userDTO = new UserDTO(existUser);

            return new CustomOauth2User(userDTO);
        }

    }
}
