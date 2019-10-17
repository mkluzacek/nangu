package nangu.test.demoapp.controllers.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michal Kluzacek
 *
 * Api object defining filter for message searching
 */
public class MessageFilter {

  private final String userName;
  private final String textContained;

  @JsonCreator
  public MessageFilter(@JsonProperty("userName") String userName,@JsonProperty("textContained") String textContained) {
    this.userName = userName;
    this.textContained = textContained;
  }

  public String getUserName() {
    return userName;
  }

  public String getTextContained() {
    return textContained;
  }
}
