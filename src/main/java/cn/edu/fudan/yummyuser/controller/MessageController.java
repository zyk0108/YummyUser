package cn.edu.fudan.yummyuser.controller;

import cn.edu.fudan.yummyuser.domain.Message;
import cn.edu.fudan.yummyuser.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/session/{sessionId}/message")
    public ResponseEntity<?> sendMessages(@PathVariable("sessionId") Long sessionId,
                                          @RequestParam("userId") Long userId,
                                          @RequestBody List<Message> messages
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/session/{sessionId}/message")
    public ResponseEntity<?> pollMessages(@PathVariable("sessionId") Long sessionId,
                                          @RequestParam("lastId") Long lastId,
                                          @RequestParam(value = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
