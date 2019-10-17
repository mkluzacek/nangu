package nangu.test.demoapp.services;

import nangu.test.demoapp.dao.UserDao;
import nangu.test.demoapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Michal Kluzacek
 */

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User createUser(User user) {
    userDao.insert(user);
    return user;
  }

  @Override
  public User selectByUserName(String userName) {
    return userDao.selectByUserName(userName);
  }

  @Override
  public boolean doesUserExists(User user) {
    return userDao.userExists(user);
  }
}
