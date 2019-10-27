package edu.ucmo.kcfedapp.controller;

import edu.ucmo.kcfedapp.dto.MessageDto;
import edu.ucmo.kcfedapp.model.PrivateMessage;
import edu.ucmo.kcfedapp.repository.PrivateMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/private/api/message")
public class PrivateMessageController {

    private final PrivateMessageRepository privateMessageRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PrivateMessageController(PrivateMessageRepository messageRepository) {
        this.privateMessageRepository = messageRepository;
    }

    @GetMapping
    public List<PrivateMessage> getPPrivateMessages() {
        List<PrivateMessage> allMessages = privateMessageRepository.findAll();
        logger.info("Returning all private messages: {}", allMessages);
        return allMessages;
    }

    @PostMapping
    public PrivateMessage createPrivateMessage(@RequestBody MessageDto message) {
        PrivateMessage newMessage = new PrivateMessage(message.getText());
        return privateMessageRepository.save(newMessage);
    }
}
