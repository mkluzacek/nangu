package nangu.test.demoapp.dao;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Kluzacek
 *
 * Abstract class for all DAO tests
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class AbstractDaoTest {

}
