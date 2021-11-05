package cn.edu.fudan.yummyuser.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private Long sessionId;
    private Long uid;
    private Long seqId;
    private String raw;
    private LocalDateTime createdAt;
}
