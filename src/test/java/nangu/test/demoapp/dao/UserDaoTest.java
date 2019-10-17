package nangu.test.demoapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nangu.test.demoapp.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link UserDao}
 */

public class UserDaoTest extends AbstractDaoTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void insertAndSelectByUserNameTest() {
    //prepare test data
    String userName= "userName";
    String password = "password";

    userDao.insert(new User(userName, password));

    User result = userDao.selectByUserName(userName);

    assertEquals(userName, result.getUserName());
    assertEquals(password, result.getPassword());
  }

  @Test
    public void testUserExists() {
    User testUser = new User("userName", "password");
    assertFalse(userDao.userExists(testUser));

    userDao.insert(testUser);

    assertTrue(userDao.userExists(testUser));
  }

}
