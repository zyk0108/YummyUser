package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.domain.Member;
import cn.edu.fudan.yummyuser.domain.Message;
import cn.edu.fudan.yummyuser.domain.Session;
import cn.edu.fudan.yummyuser.domain.SessionState;
import cn.edu.fudan.yummyuser.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
@RequiredArgsConstructor
public class SessionStateService {
    private final RedisTemplate<String, Object> redisTemplate;

    public List<SessionState> fetchStates(Long uid) {
        final String unreadKey = sessionStateUnreadKey(uid);
        final String messageKey = sessionStateMessageKey(uid);
        final Map<Object, Object> unreadMap = redisTemplate.opsForHash().entries(unreadKey);
        final Map<Object, Object> messageMap = redisTemplate.opsForHash().entries(messageKey);
        final ArrayList<SessionState> result = new ArrayList<>(unreadMap.size());
        for (Entry<Object, Object> ent : unreadMap.entrySet()) {
            result.add(SessionState.of(((Long) ent.getKey()), ((Long) ent.getValue()),
                    ((Message) messageMap.getOrDefault(ent.getKey(), null))));
        }
        result.sort((s1, s2) -> {
            if (s1.getLastMessage() == null) {
                return 1;
            }
            if (s2.getLastMessage() == null) {
                return -1;
            }
            return Long.compare(s2.getLastMessage().getId(), s1.getLastMessage().getId());
        });
        return result;
    }

    public boolean updateState(Long uid, Long sessionId, Message message) {
        final String unreadKey = sessionStateUnreadKey(uid);
        final String messageKey = sessionStateMessageKey(uid);
        redisTemplate.opsForHash().put(messageKey, sessionId, message);
        return redisTemplate.opsForHash().increment(unreadKey, sessionId, 1L) != 0;
    }

    public void readoutState(Long uid, Long sessionId) {
        final String unreadKey = sessionStateUnreadKey(uid);
        redisTemplate.opsForHash().put(unreadKey, sessionId, 0);
    }

    public boolean closeState(Long uid, Long sessionId) {
        final String unreadKey = sessionStateUnreadKey(uid);
        final String messageKey = sessionStateMessageKey(uid);
        boolean res = redisTemplate.opsForHash().delete(unreadKey, sessionId) != 0;
        res = res && redisTemplate.opsForHash().delete(messageKey, sessionId) != 0;
        return res;
    }

    public static String sessionStateUnreadKey(Long uid) {
        return RedisUtils.concatKeys(SessionState.class.getSimpleName(), uid, "unread");
    }

    public static String sessionStateMessageKey(Long uid) {
        return RedisUtils.concatKeys(SessionState.class.getSimpleName(), uid, "lastMessage");
    }
}
