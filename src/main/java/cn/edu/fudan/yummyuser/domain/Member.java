package cn.edu.fudan.yummyuser.domain;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private Long sessionId;
    private Long uid;
}
