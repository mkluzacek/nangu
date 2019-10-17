package nangu.test.demoapp.service;

import com.googlecode.easymockrule.EasyMockRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Michal Kluzacek
 *
 * Abstract class for all service tests
 */

@RunWith(SpringRunner.class)
public abstract class AbstractServiceTest {

  @Rule
  public EasyMockRule mocks = new EasyMockRule(this);

}
