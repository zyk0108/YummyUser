package cn.edu.fudan.yummyuser.domain;

import lombok.Data;

@Data
public class User {
    private Long uid;
    private String email;
    private String phoneNum;
    private String password;
}
