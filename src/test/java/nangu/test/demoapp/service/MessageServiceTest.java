package nangu.test.demoapp.service;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.googlecode.easymockrule.Mock;
import com.googlecode.easymockrule.TestSubject;
import java.util.Collections;
import java.util.List;
import nangu.test.demoapp.dao.MessageDao;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.exception.UnauthorizedException;
import nangu.test.demoapp.services.MessageServiceImpl;
import nangu.test.demoapp.services.UserService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link nangu.test.demoapp.services.MessageServiceImpl}
 */
public class MessageServiceTest extends AbstractServiceTest {

  @TestSubject
  private MessageServiceImpl messageService;

  @Mock
  private MessageDao messageDaoMock;
  @Mock
  private UserService userServiceMock;

  private Message testMessage;
  private User testUser;

  @Before
  public void initTestData() {
    testMessage = new Message(10, "Some text", "userName");
    testUser = new User("userName", "password");
  }

  @Test
  public void testCreateMessageSuccess() {
    //mock behaviour
    messageDaoMock.insert(testMessage);

    mocks.replayAll();

    Message result = messageService.createMessage(testMessage);

    assertEquals(testMessage.getText(), result.getText());
    assertEquals(testMessage.getUserName(), result.getUserName());
  }

  @Test
  public void testGetByIdSuccess() {
    //mock behaviour
    expect(messageDaoMock.getById(10)).andReturn(testMessage);

    mocks.replayAll();

    Message result = messageService.getById(10);

    assertEquals(testMessage.getText(), result.getText());
    assertEquals(testMessage.getUserName(), result.getUserName());
  }

  @Test
  public void testGetAllMessagesOfSelectedUserSuccess() {
    //mock behaviour
    expect(messageDaoMock.getAllMessagesOfSelectedUser("userName"))
        .andReturn(Collections.singletonList(testMessage));

    mocks.replayAll();

    List<Message> results = messageService.getAllMessagesOfSelectedUser("userName");

    assertEquals(1, results.size());
    assertEquals(testMessage.getText(), results.get(0).getText());
    assertEquals(testMessage.getUserName(), results.get(0).getUserName());
  }

  @Test
  public void testUpdateMessageSuccess() {
    //mock behaviour
    expect(userServiceMock.doesUserExists(testUser)).andReturn(true);
    messageDaoMock.updateMessage(testMessage);

    mocks.replayAll();

    Message result = messageService.updateMessage(testMessage, testUser);

    assertEquals(testMessage.getText(), result.getText());
    assertEquals(testMessage.getUserName(), result.getUserName());
  }

  @Test
  public void testUpdateMessageUnauthorized() {
    //mock behaviour
    expect(userServiceMock.doesUserExists(testUser)).andReturn(false);

    mocks.replayAll();

    try {
      Message result = messageService.updateMessage(testMessage, testUser);
      fail("Unauthorized exception expected");
    } catch (UnauthorizedException e) {
      //success
    }
  }

  @Test
  public void testDeleteMessageSuccess() {
    //mock behaviour
    expect(messageDaoMock.getById(testMessage.getId())).andReturn(testMessage);
    expect(userServiceMock.doesUserExists(testUser)).andReturn(true);
    messageDaoMock.deleteMessage(testMessage.getId());

    mocks.replayAll();

    messageService.deleteMessage(testMessage.getId(), testUser);
  }

  @Test
  public void testDeleteMessageUnauthorized() {
    //mock behaviour
    expect(messageDaoMock.getById(testMessage.getId())).andReturn(testMessage);
    expect(userServiceMock.doesUserExists(testUser)).andReturn(false);

    mocks.replayAll();

    try {
      messageService.deleteMessage(testMessage.getId(), testUser);
      fail("Unauthorized exception expected");
    } catch (UnauthorizedException e) {
      //success
    }
  }

}
