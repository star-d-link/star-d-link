package com.udemy.star_d_link.global.common.dto;

import lombok.Builder;

@Builder
public class ResponseDto<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> onSuccess(String message, T data) {
        return ResponseDto.<T>builder()
            .status("success")
            .message(message)
            .data(data)
            .build();
    }
}