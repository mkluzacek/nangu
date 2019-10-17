package nangu.test.demoapp.service;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import com.googlecode.easymockrule.Mock;
import com.googlecode.easymockrule.TestSubject;
import nangu.test.demoapp.dao.UserDao;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.services.UserServiceImpl;
import org.junit.Test;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link nangu.test.demoapp.services.UserServiceImpl}
 */
public class UserServiceTest extends AbstractServiceTest{

  @TestSubject
  private UserServiceImpl userService;
  @Mock
  private UserDao userDaoMock;

  @Test
  public void testCreateUserSuccess() {
    //mock behaviour
    User testUser = new User("userName", "password");

    userDaoMock.insert(testUser);

    mocks.replayAll();

    User result = userService.createUser(testUser);

    assertEquals(testUser.getUserName(), result.getUserName());
    assertEquals(testUser.getPassword(), result.getPassword());
  }

  @Test
  public void testSelectByUserName() {
    //mock behaviour
    User testUser = new User("userName", "password");

    expect(userDaoMock.selectByUserName("userName")).andReturn(testUser);

    mocks.replayAll();

    User result = userService.selectByUserName("userName");

    assertEquals(testUser.getUserName(), result.getUserName());
    assertEquals(testUser.getPassword(), result.getPassword());
  }

  @Test
  public void testUserExists() {
    //mock behaviour
    User testUser = new User("userName", "password");

    expect(userDaoMock.userExists(testUser)).andReturn(true);

    mocks.replayAll();

    userService.doesUserExists(testUser);
  }

}
