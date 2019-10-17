package nangu.test.demoapp.controller;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.googlecode.easymockrule.Mock;
import com.googlecode.easymockrule.TestSubject;
import java.util.Arrays;
import java.util.Collections;
import nangu.test.demoapp.controllers.MessageController;
import nangu.test.demoapp.controllers.advice.ControllerExceptionHandler;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.exception.UnauthorizedException;
import nangu.test.demoapp.services.MessageService;
import org.easymock.Capture;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Michal Kluzacek
 *
 * Tests for {@link MessageController}
 */
public class MessageControllerTest extends AbstractControllerTest {

  @TestSubject
  private MessageController messageController;

  @Mock
  private MessageService messageServiceMock;

  @Before
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(messageController)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
  }

  @Test
  public void testGetMessagesByFilterUserNameOnly() throws Exception {
    Capture<MessageFilter> messageFilterCapture = new Capture<>();

    //mock behaviour
    expect(messageServiceMock.getAllMessagesByFilter(capture(messageFilterCapture)))
        .andReturn(Collections.singletonList(new Message("Test text", "userName")));
    mocks.replayAll();

    mockMvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"userName\": \"userName\"}")).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", Matchers.is("Test text")))
        .andExpect(jsonPath("$[0].userName", Matchers.is("userName")));

    assertEquals("userName", messageFilterCapture.getValue().getUserName());
    assertNull(messageFilterCapture.getValue().getTextContained());
  }

  @Test
  public void testGetMessagesByFilterContainedTextOnly() throws Exception {
    Capture<MessageFilter> messageFilterCapture = new Capture<>();

    //mock behaviour
    expect(messageServiceMock.getAllMessagesByFilter(capture(messageFilterCapture)))
        .andReturn(Collections.singletonList(new Message("Random text here", "userName")));
    mocks.replayAll();

    mockMvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"textContained\": \"Random text\"}")).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", Matchers.is("Random text here")))
        .andExpect(jsonPath("$[0].userName", Matchers.is("userName")));

    assertEquals("Random text", messageFilterCapture.getValue().getTextContained());
    assertNull(messageFilterCapture.getValue().getUserName());
  }

  @Test
  public void testGetMessagesByFilterByBothContainedTextAndUsername() throws Exception {
    Capture<MessageFilter> messageFilterCapture = new Capture<>();

    //mock behaviour
    expect(messageServiceMock.getAllMessagesByFilter(capture(messageFilterCapture)))
        .andReturn(Collections.singletonList(new Message("Random text here", "userName")));
    mocks.replayAll();

    mockMvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"textContained\": \"Random text\", \"userName\": \"userName\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", Matchers.is("Random text here")))
        .andExpect(jsonPath("$[0].userName", Matchers.is("userName")));

    assertEquals("Random text", messageFilterCapture.getValue().getTextContained());
    assertEquals("userName", messageFilterCapture.getValue().getUserName());
  }

  @Test
  public void testGetMessagesByFilterByBothContainedTextAndUsernameMultipleMessages()
      throws Exception {
    Capture<MessageFilter> messageFilterCapture = new Capture<>();

    //mock behaviour
    expect(messageServiceMock.getAllMessagesByFilter(capture(messageFilterCapture)))
        .andReturn(Arrays.asList(new Message("Random text here", "userName"),
            new Message("Other text here", "userName")));
    mocks.replayAll();

    mockMvc.perform(get("/messages").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"textContained\": \"text\", \"userName\": \"userName\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", Matchers.is("Random text here")))
        .andExpect(jsonPath("$[0].userName", Matchers.is("userName")))
        .andExpect(jsonPath("$[1].text", Matchers.is("Other text here")))
        .andExpect(jsonPath("$[1].userName", Matchers.is("userName")));

    assertEquals("text", messageFilterCapture.getValue().getTextContained());
    assertEquals("userName", messageFilterCapture.getValue().getUserName());
  }

  @Test
  public void testCreateNewMessageSuccess() throws Exception {
    Capture<Message> messageCapture = new Capture<>();
    //mock behaviour
    expect(messageServiceMock.createMessage(capture(messageCapture)))
        .andReturn(new Message("Test text", "userName"));
    mocks.replayAll();

    mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"text\": \"Test text\", \"userName\": \"userName\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.text", Matchers.is("Test text")))
        .andExpect(jsonPath("$.userName", Matchers.is("userName")));

    assertEquals("Test text", messageCapture.getValue().getText());
    assertEquals("userName", messageCapture.getValue().getUserName());
  }

  @Test
  public void testDeleteMessageSuccess() throws Exception {
    Capture<User> userCapture = new Capture<>();
    Capture<Long> longCapture = new Capture<>();
    //mock behaviour
    messageServiceMock.deleteMessage(capture(longCapture), capture(userCapture));
    mocks.replayAll();

    mockMvc.perform(delete("/messages/10").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"userName\": \"userName\", \"password\": \"password\"}"))
        .andExpect(status().isOk());

    assertEquals("password", userCapture.getValue().getPassword());
    assertEquals("userName", userCapture.getValue().getUserName());
    assertEquals(10, longCapture.getValue().longValue());
  }

  @Test
  public void testDeleteMessageUnauthorized() throws Exception {
    Capture<User> userCapture = new Capture<>();
    Capture<Long> longCapture = new Capture<>();
    //mock behaviour
    messageServiceMock.deleteMessage(capture(longCapture), capture(userCapture));
    expectLastCall().andThrow(new UnauthorizedException("Cannot delete other people messages."));
    mocks.replayAll();

    mockMvc.perform(delete("/messages/10").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\"userName\": \"userName\", \"password\": \"password\"}"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Cannot delete other people messages."));

    assertEquals("password", userCapture.getValue().getPassword());
    assertEquals("userName", userCapture.getValue().getUserName());
    assertEquals(10, longCapture.getValue().longValue());
  }

  @Test
  public void testUpdateMessageSuccess() throws Exception {
    Capture<User> userCapture = new Capture<>();
    Capture<Message> messageCapture = new Capture<>();
    //mock behaviour
    expect(messageServiceMock.updateMessage(capture(messageCapture), capture(userCapture)))
        .andReturn(new Message(10, "Test text", "userName"));
    mocks.replayAll();

    mockMvc.perform(put("/messages/10").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(
            "{\"text\":\"Test text\",\"user\" : {\"userName\": \"userName\", \"password\": \"password\"}}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.text", Matchers.is("Test text")))
        .andExpect(jsonPath("$.id", Matchers.is(10)))
        .andExpect(jsonPath("$.userName", Matchers.is("userName")));

    assertEquals("password", userCapture.getValue().getPassword());
    assertEquals("userName", userCapture.getValue().getUserName());
    assertEquals("Test text", messageCapture.getValue().getText());
    assertEquals(10, messageCapture.getValue().getId());
  }

  @Test
  public void testUpdateMessageUnauthorized() throws Exception {
    Capture<User> userCapture = new Capture<>();
    Capture<Message> messageCapture = new Capture<>();
    //mock behaviour
    messageServiceMock.updateMessage(capture(messageCapture), capture(userCapture));
    expectLastCall().andThrow(new UnauthorizedException("Cannot update other people messages."));
    mocks.replayAll();

    mockMvc.perform(put("/messages/10").contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(
            "{\"text\":\"Test text\",\"user\" : {\"userName\": \"userName\", \"password\": \"password\"}}"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("Cannot update other people messages."));

    assertEquals("password", userCapture.getValue().getPassword());
    assertEquals("userName", userCapture.getValue().getUserName());
    assertEquals("Test text", messageCapture.getValue().getText());
    assertEquals(10, messageCapture.getValue().getId());
  }

}
