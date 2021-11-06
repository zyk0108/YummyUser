package cn.edu.fudan.yummyuser.service;

import cn.edu.fudan.yummyuser.domain.Message;
import cn.edu.fudan.yummyuser.mapper.IMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final IMessageMapper messageMapper;
    private final PlatformTransactionManager trxManager;

    public int sendMessages(Long sessionId, Long senderId, List<Message> messages) {
        for (Message msg : messages) {
            msg.setSessionId(sessionId);
            msg.setUid(senderId);
        }
        final TransactionStatus trxStatus = trxManager.getTransaction(TransactionDefinition.withDefaults());
        int res = -1;
        try {
            res = messageMapper.insertMessages(messages);
        } catch (Exception ex) {
            trxManager.rollback(trxStatus);
        }
        trxManager.commit(trxStatus);
        return res;
    }

    public List<Message> pollMessagesAsc(Long sessionId, Long firstId, int limit) {
        return messageMapper.selectMessagesAsc(sessionId, firstId, limit);
    }

    public List<Message> pollMessagesDesc(Long sessionId, Long lastId, int limit) {
        return messageMapper.selectMessagesDesc(sessionId, lastId, limit);
    }

}
