package br.com.cursomicroservices.productapi.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handlerValidationException(ValidationException validationException) {
    var details =  new ExceptionDetail();
    details.setStatus(HttpStatus.BAD_REQUEST.value());
    details.setMessage(validationException.getMessage());
    return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handlerAuthenticationException(AuthenticationException authenticationException) {
    var details =  new ExceptionDetail();
    details.setStatus(HttpStatus.UNAUTHORIZED.value());
    details.setMessage(authenticationException.getMessage());
    return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
  }
}
