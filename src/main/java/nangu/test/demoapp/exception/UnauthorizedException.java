package nangu.test.demoapp.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Michal Kluzacek
 *
 * Exception thrown when trying to execute action without proper authorization
 */
public class UnauthorizedException extends AuthenticationException {

  public UnauthorizedException(String msg) {
    super(msg);
  }
}
