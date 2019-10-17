package nangu.test.demoapp.controllers;

import java.util.List;
import nangu.test.demoapp.controllers.domain.MessageFilter;
import nangu.test.demoapp.controllers.domain.MessageUpdate;
import nangu.test.demoapp.domain.Message;
import nangu.test.demoapp.domain.User;
import nangu.test.demoapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michal Kluzacek
 *
 * Controller for all message actions
 */

@RestController
@RequestMapping("/messages")
public class MessageController {

  @Autowired
  private MessageService messageService;

  /**
   * Gets all messages matching the filter
   * @param filter object defining filtering properties
   * @return all messages matching filter properties
   */
  @GetMapping()
  public ResponseEntity<List<Message>> getMessagesByFilter(
      @RequestBody(required = false) MessageFilter filter) {
    List<Message> messages = messageService.getAllMessagesByFilter(filter);

    return new ResponseEntity<>(messages, HttpStatus.OK);
  }

  /**
   * Updates selected message
   * @param id message id
   * @param messageUpdate object defining what to update and authentication properties
   * @return updated message
   */
  @PutMapping("/{id}")
  public ResponseEntity<Message> updateMessage(@PathVariable long id,
      @RequestBody MessageUpdate messageUpdate) {
    return new ResponseEntity<>(messageService.updateMessage(
        new Message(id, messageUpdate.getText(), messageUpdate.getUser().getUserName()),
        messageUpdate.getUser()), HttpStatus.OK);
  }

  /**
   * Creates new message
   * @param message properties of the message
   * @return created message
   */
  @PostMapping
  public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
  }

  /**
   * Deletes selected message
   * @param id message id
   * @param user authentication User object
   */
  @DeleteMapping("/{id}")
  public ResponseEntity deleteMessage(@PathVariable long id, @RequestBody User user) {
    messageService.deleteMessage(id, user);

    return new ResponseEntity(HttpStatus.OK);
  }
}
