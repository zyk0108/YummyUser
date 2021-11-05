package cn.edu.fudan.yummyuser.domain;


import lombok.Data;

@Data
public class SessionState {
    private Long sessionId;
    private Long unread;
    private Message lastMessage;
}
