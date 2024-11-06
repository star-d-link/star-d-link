package com.udemy.star_d_link.user.exception;

public enum UserExceptions {

    NOT_FOUND("NOT_FOUND", 404),
    DUPLICATE("DUPLICATE", 409),
    INVALID("INVALID", 400),
    BAD_REQUEST("BAD_REQUEST", 400),
    FORBIDDEN("FORBIDDEN", 403),
    UNAUTHORIZED("UNAUTHORIZED", 401),
    ;

    private final UserTaskException userTaskException;

    UserExceptions(String msg, int code) {
        this.userTaskException = new UserTaskException(msg, code);
    }

    public UserTaskException get() {
        return userTaskException;
    }
}
