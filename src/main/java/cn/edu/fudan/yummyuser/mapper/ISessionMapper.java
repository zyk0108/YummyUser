package cn.edu.fudan.yummyuser.mapper;

import org.apache.ibatis.annotations.Insert;

public interface ISessionMapper {
    @Insert("insert ignore into session(name) values(#{name}")
    int insertSession(String name);
}
