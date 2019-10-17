package nangu.test.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michal Kluzacek
 *
 * Object defining the User
 */
public class User {

  private final String userName;
  private final String password;

  @JsonCreator
  public User(@JsonProperty("userName") String userName, @JsonProperty("password") String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }
}
