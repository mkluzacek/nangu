package nangu.test.demoapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link MessageDao}
 */
public class MessageDaoTest extends AbstractDaoTest{

  @Autowired
  private MessageDao messageDao;

  @Autowired
  private UserDao userDao;

  @Before
  public void createUser() {
    userDao.insert(new User("testUsername", "password"));
  }

  @Test
  public void testInsertAndSelectAllMessagesOfGivenUser() {
    Message firstMessageToInsert = new Message("Random text", "testUsername");
    Message secondMessageToInsert = new Message("Random text 2", "testUsername");

    messageDao.insert(firstMessageToInsert);
    messageDao.insert(secondMessageToInsert);


    List<Message> results = messageDao.getAllMessagesOfSelectedUser("testUsername");

    assertEquals(2, results.size());

    assertEquals(firstMessageToInsert.getId() + 1, secondMessageToInsert.getId());
    assertEquals("Random text", results.get(0).getText());
    assertEquals("Random text 2", results.get(1).getText());

    assertEquals("testUsername", results.get(0).getUserName());
    assertEquals("testUsername", results.get(1).getUserName());
  }

  @Test
  public void testUpdate() {
    Message messageToInsert = new Message("Some text", "testUsername");

    messageDao.insert(messageToInsert);

    Message updatedMessage = new Message(messageToInsert.getId(), "New text", null);

    messageDao.updateMessage(updatedMessage);

    Message result = messageDao.getById(messageToInsert.getId());

    assertEquals(messageToInsert.getId(), result.getId());
    assertEquals("New text", result.getText());
    assertEquals("testUsername", result.getUserName());
  }

  @Test
  public void testDelete() {
    Message messageToInsert = new Message("Some text", "testUsername");

    messageDao.insert(messageToInsert);

    assertNotNull(messageDao.getById(messageToInsert.getId()));

    messageDao.deleteMessage(messageToInsert.getId());

    assertNull(messageDao.getById(messageToInsert.getId()));
  }

  @Test
  public void getByFilterOnlyUserName() {
    Message firstMessageToInsert = new Message("Random something", "testUsername");
    Message secondMessageToInsert = new Message("Random text 2", "testUsername");

    messageDao.insert(firstMessageToInsert);
    messageDao.insert(secondMessageToInsert);

    List<Message> result = messageDao.getAllMessagesByFilter(new MessageFilter("testUsername", null));

    assertEquals(2, result.size());

    assertEquals(firstMessageToInsert.getId() + 1, secondMessageToInsert.getId());
    assertEquals("Random something", result.get(0).getText());
    assertEquals("Random text 2", result.get(1).getText());

    assertEquals("testUsername", result.get(0).getUserName());
    assertEquals("testUsername", result.get(1).getUserName());
  }

  @Test
  public void getByFilterOnlyText() {
    Message firstMessageToInsert = new Message("Random something", "testUsername");
    Message secondMessageToInsert = new Message("Random text 2", "testUsername");

    messageDao.insert(firstMessageToInsert);
    messageDao.insert(secondMessageToInsert);

    List<Message> result = messageDao.getAllMessagesByFilter(new MessageFilter(null, "text"));

    assertEquals(1, result.size());
    assertEquals("testUsername", result.get(0).getUserName());
    assertEquals("Random text 2", result.get(0).getText());
  }

  @Test
  public void getByFilterNameAndTextNoFind() {
    Message firstMessageToInsert = new Message("Random something", "testUsername");
    userDao.insert(new User("testUserName", "password"));
    Message secondMessageToInsert = new Message("Random text 2", "testUserName");

    messageDao.insert(firstMessageToInsert);
    messageDao.insert(secondMessageToInsert);

    List<Message> result = messageDao.getAllMessagesByFilter(new MessageFilter("testUsername", "text"));

    assertEquals(0, result.size());
  }

  @Test
  public void getByFilterNameAndTextOneFind() {
    Message firstMessageToInsert = new Message("Random text something", "testUsername");
    userDao.insert(new User("testUserName", "password"));
    Message secondMessageToInsert = new Message("Random text 2", "testUserName");

    messageDao.insert(firstMessageToInsert);
    messageDao.insert(secondMessageToInsert);

    List<Message> result = messageDao.getAllMessagesByFilter(new MessageFilter("testUserName", "text"));

    assertEquals(1, result.size());
    assertEquals("testUserName", result.get(0).getUserName());
    assertEquals("Random text 2", result.get(0).getText());
  }

}
