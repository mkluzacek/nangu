package nangu.test.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michal Kluzacek
 *
 * Object defining the Message
 */
public class Message {

  private long id;

  private final String text;
  private final String userName;

  public Message(String text, String userName) {
    this.text = text;
    this.userName = userName;
  }

  @JsonCreator
  public Message(@JsonProperty("id") long id,@JsonProperty("text") String text,@JsonProperty("userName") String userName) {
    this.id = id;
    this.text = text;
    this.userName = userName;
  }

  public long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getUserName() {
    return userName;
  }
}
