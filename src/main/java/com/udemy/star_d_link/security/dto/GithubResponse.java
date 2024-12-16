package com.udemy.star_d_link.security.dto;

import java.util.Map;

public class GithubResponse implements Oauth2Response {

    private Map<String, Object> attributes;

    public GithubResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email") == null ? (String) attributes.get("login")
            : (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
