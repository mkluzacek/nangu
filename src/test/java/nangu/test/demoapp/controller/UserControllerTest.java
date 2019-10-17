package nangu.test.demoapp.controller;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.googlecode.easymockrule.Mock;
import com.googlecode.easymockrule.TestSubject;
import nangu.test.demoapp.controllers.UserController;
import nangu.test.demoapp.controllers.advice.ControllerExceptionHandler;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.services.UserService;
import org.easymock.Capture;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link UserController}
 */
public class UserControllerTest extends AbstractControllerTest {

  @TestSubject
  private UserController userController;

  @Mock
  private UserService userService;

  @Before
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
  }

  @Test
  public void testCreateUserSuccess() throws Exception {
    User newUser = new User("userName", "password");

    Capture<User> userCapture = new Capture<>();

    expect(userService.createUser(capture(userCapture))).andReturn(newUser);

    mocks.replayAll();

    mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"userName\": \"userName\", \"password\": \"password\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userName", Matchers.is("userName")))
        .andExpect(jsonPath("$.password", Matchers.is("password")));

    assertEquals(newUser.getUserName(), userCapture.getValue().getUserName());
    assertEquals(newUser.getPassword(), userCapture.getValue().getPassword());
  }

}
