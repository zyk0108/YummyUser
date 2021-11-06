package cn.edu.fudan.yummyuser.controller;

import cn.edu.fudan.yummyuser.service.SessionService;
import cn.edu.fudan.yummyuser.service.SessionStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final SessionStateService sessionStateService;

    @PutMapping("/user/session")
    public ResponseEntity<?> openSession(@RequestParam("uid1") Long uid1, @RequestParam("uid2") Long uid2) {
        return null;
    }

    @DeleteMapping("/user/session/{sessionId}")
    public ResponseEntity<?> closeSession(@PathVariable("sessionId") Long sessionId) {
        return null;
    }

    @DeleteMapping("/user/{userId}/session/states")
    public ResponseEntity<?> fetchSessionStates(@PathVariable("userId") Long userId) {
        return null;
    }

    @DeleteMapping("/user/{userId}/session/{sessionId}/states")
    public ResponseEntity<?> readoutSessionState(@PathVariable("userId") Long userId,
                                                 @PathVariable("sessionId") Long sessionId) {
        return null;
    }

}
