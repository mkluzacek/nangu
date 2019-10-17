package nangu.test.demoapp.controllers.advice;

import nangu.test.demoapp.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Michal Kluzacek
 *
 * Handler for all expections from rest controllers
 */

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<String> unauthorizedExceptionHandler(UnauthorizedException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
  }

}
