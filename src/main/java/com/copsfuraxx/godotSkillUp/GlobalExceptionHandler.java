package com.copsfuraxx.godotSkillUp;

import com.copsfuraxx.godotSkillUp.exceptions.UserInTokenNotValid;
import com.copsfuraxx.godotSkillUp.models.responses.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse makeErrorResponse(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(makeErrorResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserInTokenNotValid.class)
    public ResponseEntity<ErrorResponse> handleUserInTokenNotValidException(UserInTokenNotValid ex) {
        return new ResponseEntity<>(makeErrorResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        return new ResponseEntity<>(new ErrorResponse("Vous n'avez pas le role pour faire cette action"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException() {
        return new ResponseEntity<>(new ErrorResponse("Token Expiré"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(makeErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
