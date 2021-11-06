package cn.edu.fudan.yummyuser.domain;


import lombok.Data;

@Data(staticConstructor = "of")
public class SessionState {
    private final Long sessionId;
    private final Long unread;
    private final Message lastMessage;
}
