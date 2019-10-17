package nangu.test.demoapp.services;

import java.util.List;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;

/**
 * @author Michal Kluzacek
 *
 * Service for all action related with Messages
 */
public interface MessageService {

  /**
   * Inserts new message into DB
   *
   * @param message message to be inserted
   */
  Message createMessage(Message message);

  /**
   * Gets message with given id
   *
   * @param messageId id of the message to get
   * @return Message with the given id
   */
  Message getById(long messageId);

  /**
   * Gets all messages of given user
   *
   * @param userName username of the user
   * @return all messages of selected user
   */
  List<Message> getAllMessagesOfSelectedUser(String userName);

  /**
   * Updates the given message
   *
   * @param message the message to be updated
   * @throws nangu.test.demoapp.exception.UnauthorizedException when executed by other user than
   * owner
   */
  Message updateMessage(Message message, User user);

  /**
   * Deletes message with given id
   * @param messageId id of the message
   * @throws nangu.test.demoapp.exception.UnauthorizedException when executed by other user than
   * owner
   */
  void deleteMessage(long messageId, User user);

  /**
   * Gets all the messages matching the filter properties
   * @param messageFilter object defining the filter options
   * @return all messages matching the filter
   */
  List<Message> getAllMessagesByFilter(MessageFilter messageFilter);

}
