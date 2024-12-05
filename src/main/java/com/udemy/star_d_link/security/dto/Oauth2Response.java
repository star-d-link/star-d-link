package com.udemy.star_d_link.security.dto;

public interface Oauth2Response {
    // 제공자 naver, google, kakao 등
    String getProvider();
    // 제공자에서 발급해주는 아이디(번호)
    String getProviderId();

    //이메일
    String getEmail();

    //사용자 설명(설정한 이름)
    String getName();
}
