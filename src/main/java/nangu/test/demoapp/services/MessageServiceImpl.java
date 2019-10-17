package nangu.test.demoapp.services;

import java.util.List;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.dao.MessageDao;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Michal Kluzacek
 */
@Service
public class MessageServiceImpl implements MessageService{

  @Autowired
  private MessageDao messageDao;

  @Autowired
  private UserService userService;

  @Override
  public Message createMessage(Message message) {
    messageDao.insert(message);
    return message;
  }

  @Override
  public Message getById(long messageId) {
    return messageDao.getById(messageId);
  }

  @Override
  public List<Message> getAllMessagesOfSelectedUser(String userName) {
    return messageDao.getAllMessagesOfSelectedUser(userName);
  }

  @Override
  public Message updateMessage(Message message, User user) {
    if(!doestMessageBelongToUser(message, user)) {
      throw new UnauthorizedException("Permission denied.");
    }

    messageDao.updateMessage(message);
    return message;
  }

  @Override
  public void deleteMessage(long messageId, User user) {
    if(!doestMessageBelongToUser(getById(messageId), user)) {
      throw new UnauthorizedException("Permission denied.");
    }

    messageDao.deleteMessage(messageId);
  }

  @Override
  public List<Message> getAllMessagesByFilter(MessageFilter messageFilter) {
    return messageDao.getAllMessagesByFilter(messageFilter);
  }

  private boolean doestMessageBelongToUser(Message message, User user) {
    return message.getUserName().equals(user.getUserName()) && userService.doesUserExists(user);
  }
}
