package cn.edu.fudan.yummyuser.controller;

import cn.edu.fudan.yummyuser.domain.Session;
import cn.edu.fudan.yummyuser.domain.SessionState;
import cn.edu.fudan.yummyuser.service.SessionService;
import cn.edu.fudan.yummyuser.service.SessionStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final SessionStateService sessionStateService;

    @PutMapping("/user/session")
    public ResponseEntity<?> openSession(@RequestParam("uid1") Long uid1, @RequestParam("uid2") Long uid2) {
        final Session session = sessionService.openSession(uid1, uid2);
        return ResponseEntity.ok().body(session);
    }

    @DeleteMapping("/user/{userId}/session/{sessionId}")
    public ResponseEntity<?> closeSession(@PathVariable("userId") Long userId,
                                          @PathVariable("sessionId") Long sessionId) {
        final boolean result = sessionStateService.closeState(userId, sessionId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/user/{userId}/session/states")
    public ResponseEntity<?> fetchSessionStates(@PathVariable("userId") Long userId) {
        final List<SessionState> states = sessionStateService.fetchStates(userId);
        return ResponseEntity.ok(states);
    }

    @DeleteMapping("/user/{userId}/session/{sessionId}/states")
    public ResponseEntity<?> readoutSessionState(@PathVariable("userId") Long userId,
                                                 @PathVariable("sessionId") Long sessionId) {
        sessionStateService.readoutState(userId, sessionId);
        return ResponseEntity.ok().build();
    }

}
