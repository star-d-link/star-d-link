package com.udemy.star_d_link.user.controller.advice;

import com.udemy.star_d_link.user.exception.UserTaskException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class TokenControllerAdvice {

    /**
     * UserTaskException이 발생할때 (ex: {@link com.udemy.star_d_link.user.service.UserService}의
     * read() 메소드에서 없는 사용자의 정보나 패스워드가 맞지 않는경우)
     * 예외를 처리한다.
     * @param ex
     * @return
     */
    @ExceptionHandler(UserTaskException.class)
    public ResponseEntity<Map<String, String>> handleTaskException(UserTaskException ex) {

        log.error(ex.getMessage());

        String msg = ex.getMsg();
        int status = ex.getCode();

        Map<String, String> map = Map.of("error", msg);

        return ResponseEntity.status(status).body(map);
    }

    /**
     * 접근권한이 없는 사용자가 자원에 접근하려 할때 발생하는 예외를 처리한다.
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {

        log.info("handleAccessDeniedException..............................");

        Map<String, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }
}
