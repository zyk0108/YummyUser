package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.domain.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    void sendMessages() {
        messageService.sendMessages(1L, 1L,
                Arrays.asList(
                        Message.of(1L, "message1"),
                        Message.of(2L, "message2"),
                        Message.of(3L, "message3")
                )
        );
    }
}
