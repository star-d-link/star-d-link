package com.udemy.star_d_link.security.dto;

import java.util.Map;
import java.util.Objects;

public class NaverResponse implements Oauth2Response{

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    /**
     * @return
     */
    @Override
    public String getProvider() {
        return "naver";
    }

    /**
     * @return
     */
    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    /**
     * @return
     */
    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
