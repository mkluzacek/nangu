package nangu.test.demoapp.dao;

import java.util.List;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Michal Kluzacek
 *
 * Data acces object for table T_MESSAGE
 */

@Mapper
public interface MessageDao {

  /**
   * Inserts new message into DB
   * @param message message to be inserted
   */
  void insert(@Param("message") Message message);

  /**
   * Gets message with given id
   * @param messageId id of the message to get
   * @return Message with the given id
   */
  Message getById(@Param("messageId") long messageId);

  /**
   * Gets all messages of given user
   * @param userName username of the user
   * @return all messages of selected user
   */
  List<Message> getAllMessagesOfSelectedUser(@Param("userName") String userName);

  /**
   * Gets all the messages matching the filter properties
   * @param messageFilter object defining the filter options
   * @return all messages matching the filter
   */
  List<Message> getAllMessagesByFilter(@Param("messageFilter")MessageFilter messageFilter);

  /**
   * Updates the given message
   * @param message the message to be updated
   */
  void updateMessage(@Param("message") Message message);

  /**
   * Deletes message with given id
   * @param messageId id of the message
   */
  void deleteMessage(@Param("messageId") long messageId);

}
