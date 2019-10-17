package nangu.test.demoapp.controllers.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import nangu.test.demoapp.domain.User;

/**
 * @author Michal Kluzacek
 *
 * Api object defining message update, contains authentication properties through user field
 */
public class MessageUpdate {

  private final String text;
  private final User user;

  @JsonCreator
  public MessageUpdate(@JsonProperty("text") String text, @JsonProperty("user") User user) {
    this.text = text;
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public User getUser() {
    return user;
  }
}
