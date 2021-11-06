package cn.edu.fudan.yummyuser.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Session implements Serializable {
    private Long id;
    private String name;
}
