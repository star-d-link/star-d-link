package com.udemy.star_d_link.security.dto;

import java.util.Map;
import java.util.Objects;

public class GoogleResponse implements Oauth2Response {

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }
    /**
     * @return
     */
    @Override
    public String getProvider() {
        return "google";
    }

    /**
     * @return
     */
    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
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
