package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.domain.Member;
import cn.edu.fudan.yummyuser.domain.Session;
import cn.edu.fudan.yummyuser.domain.SingleChat;
import cn.edu.fudan.yummyuser.mapper.IMemberMapper;
import cn.edu.fudan.yummyuser.mapper.ISessionMapper;
import cn.edu.fudan.yummyuser.mapper.ISingleChatMapper;
import cn.edu.fudan.yummyuser.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SessionService {
    private final ISessionMapper sessionMapper;
    private final IMemberMapper memberMapper;
    private final ISingleChatMapper singleChatMapper;

    private final PlatformTransactionManager trxManager;
    private final RedisTemplate<String, Object> redisTemplate;

    public Session openSession(Long uid1, Long uid2) {
        Long uidA = Math.min(uid1, uid2), uidB = Math.max(uid1, uid2);
        final String key = sessionKey(uidA, uidB);
        Session result = ((Session) redisTemplate.opsForValue().get(key));
        if (result != null) {
            return result;
        }
        final TransactionStatus trxStatus = trxManager.getTransaction(TransactionDefinition.withDefaults());
        boolean canCommit = true;
        try {
            final SingleChat chat = singleChatMapper.selectSingleChatSync(uidA, uidB);
            if (chat != null) {
                result = sessionMapper.selectSession(chat.getSessionId());
            }
            if (result == null) {
                result = new Session();
                result.setName(uidA + ":" + uidB);
                if (sessionMapper.insertSession(result) == 0) {
                    canCommit = false;
                    return null;
                }
                Long sessionId = result.getId();
                if (singleChatMapper.insertSingleChat(uidA, uidB, sessionId) == 0) {
                    canCommit = false;
                    return null;
                }
                final Member memberA = new Member(), memberB = new Member();
                memberA.setSessionId(sessionId);
                memberA.setUid(uidA);
                memberB.setSessionId(sessionId);
                memberB.setUid(uidB);
                memberMapper.insertMembers(Arrays.asList(memberA, memberB));
            }
        } catch (Exception ex) {
            canCommit = false;
        } finally {
            if (canCommit) {
                trxManager.commit(trxStatus);
                redisTemplate.opsForValue().set(key, result, Duration.ofSeconds(600));
            } else {
                trxManager.rollback(trxStatus);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Member> queryMembers(Long sessionId) {
        final String key = sessionMemberKey(sessionId);
        List<Member> result = (List<Member>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        try {
            result = memberMapper.selectMembers(sessionId);
        } finally {
            if (result != null) {
                redisTemplate.opsForValue().set(key, result, Duration.ofSeconds(600));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<Long, List<Member>> queryMembers(List<Long> sessionIds) {
        final List<String> keys = sessionIds.stream().map(SessionService::sessionMemberKey).
                collect(Collectors.toList());
        final List<Object> list = redisTemplate.opsForValue().multiGet(keys);
        final Map<Long, List<Member>> result = new HashMap<>(sessionIds.size());
        for (int i = 0; i < sessionIds.size(); i++) {
            List<Member> local = ((List<Member>) list.get(i));
            if (local == null) {
                local = queryMembers(sessionIds.get(i));
            }
            result.put(sessionIds.get(i), local);
        }
        return result;
    }

    public static String sessionKey(Long uid1, Long uid2) {
        return RedisUtils.concatKeys(Session.class.getSimpleName(), Math.min(uid1, uid2), Math.max(uid1, uid2));
    }

    public static String sessionMemberKey(Long sessionId) {
        return RedisUtils.concatKeys(Session.class.getSimpleName(), Member.class.getSimpleName(), sessionId);
    }

}
