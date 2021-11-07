package cn.edu.fudan.yummyuser.controller;

import cn.edu.fudan.yummyuser.domain.Member;
import cn.edu.fudan.yummyuser.domain.Message;
import cn.edu.fudan.yummyuser.service.MessageService;
import cn.edu.fudan.yummyuser.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final SessionService sessionService;

    @PostMapping("/session/{sessionId}/message")
    public ResponseEntity<?> sendMessages(@PathVariable("sessionId") Long sessionId,
                                          @RequestParam("userId") Long userId,
                                          @RequestBody List<Message> messages
    ) {
        boolean isMember = false;
        for (Member mem : sessionService.queryMembers(sessionId)) {
            if (mem.getUid().equals(userId)) {
                isMember = true;
                break;
            }
        }
        if (!isMember) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        messageService.sendMessages(sessionId, userId, messages);
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/session/{sessionId}/message")
    public ResponseEntity<?> pollMessages(@PathVariable("sessionId") Long sessionId,
                                          @RequestParam("lastId") Long lastId,
                                          @RequestParam(value = "order", defaultValue = "asc") String order
    ) {
        if (order != null) {
            order = order.toLowerCase(Locale.ROOT);
        }
        if (!"desc".equals(order)) {
            order = "asc";
        }
        final List<Message> messages;
        if ("asc".equals(order)) {
            messages = messageService.pollMessagesAsc(sessionId, lastId, 10);
        } else {
            messages = messageService.pollMessagesDesc(sessionId, lastId, 10);
        }
        return ResponseEntity.ok().body(messages);
    }


}
