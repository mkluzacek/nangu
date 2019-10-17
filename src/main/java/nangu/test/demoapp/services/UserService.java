package nangu.test.demoapp.services;

import nangu.test.demoapp.domain.User;

/**
 * @author Michal Kluzacek
 */
public interface UserService {

  /**
   * Inserts new user into DB
   * @param user user to be inserted
   */
  User createUser(User user);

  /**
   * Gets user with given userName
   * @param userName username of the user to get
   * @return user with the given userName or null if none find
   */
  User selectByUserName(String userName);

  /**
   * Checks whether the user exists
   * @param user user to be checked for existence
   * @return true when the user exists and false otherwise
   */
  boolean doesUserExists(User user);

}
