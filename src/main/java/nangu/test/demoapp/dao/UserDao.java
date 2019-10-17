package nangu.test.demoapp.dao;

import nangu.test.demoapp.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Michal Kluzacek
 *
 * Data acces object for table T_USER
 */

@Mapper
public interface UserDao {

  /**
   * Inserts new user into DB
   * @param user user to be inserted
   */
  void insert(@Param("user") User user);

  /**
   * Gets user with given userName
   * @param userName username of the user to get
   * @return user with the given userName or null if none find
   */
  User selectByUserName(@Param("userName") String userName);

  /**
   * Checks whether the user exists
   * @param user user to be checked for existence
   * @return true when the user exists and false otherwise
   */
  boolean userExists(@Param("user") User user);

}
