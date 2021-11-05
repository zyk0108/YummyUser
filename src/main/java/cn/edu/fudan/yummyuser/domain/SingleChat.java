package cn.edu.fudan.yummyuser.domain;

import lombok.Data;

@Data
public class SingleChat {
    private Long id;
    private Long sessionId;
    private Long uid1;
    private Long uid2;
}
