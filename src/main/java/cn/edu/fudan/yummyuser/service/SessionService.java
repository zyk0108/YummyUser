package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.domain.Member;
import cn.edu.fudan.yummyuser.domain.Session;
import cn.edu.fudan.yummyuser.domain.SessionState;
import cn.edu.fudan.yummyuser.domain.SingleChat;
import cn.edu.fudan.yummyuser.mapper.IMemberMapper;
import cn.edu.fudan.yummyuser.mapper.ISessionMapper;
import cn.edu.fudan.yummyuser.mapper.ISingleChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SessionService {
    private final ISessionMapper sessionMapper;
    private final IMemberMapper memberMapper;
    private final ISingleChatMapper singleChatMapper;

    private final PlatformTransactionManager trxManager;

    public Session openSession(Long uid1, Long uid2) {
        Long uidA = Math.min(uid1, uid2);
        Long uidB = Math.max(uid1, uid2);
        Session result = null;
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
                singleChatMapper.insertSingleChat(uidA, uidB, sessionId);
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
            } else {
                trxManager.rollback(trxStatus);
            }
        }
        return result;
    }

    public boolean closeSession(Long uid1, Long uid2) {
        return false;
    }

    public List<SessionState> fetchStates(Long uid) {
        return Collections.emptyList();
    }

}
