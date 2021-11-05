package cn.edu.fudan.yummyuser.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Message {
    private Long id;
    private Long sessionId;
    private Long uid;
    @NonNull
    private Long seqId;
    @NonNull
    private String raw;
    private LocalDateTime createdAt;
}
