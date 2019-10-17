package nangu.test.demoapp.controller;

import com.googlecode.easymockrule.EasyMockRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Michal Kluzacek
 *
 * Abstract class for all rest controller tests
 */

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractControllerTest {

  @Rule
  public EasyMockRule mocks = new EasyMockRule(this);

  protected MockMvc mockMvc;

}
