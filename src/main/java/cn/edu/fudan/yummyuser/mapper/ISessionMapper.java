package cn.edu.fudan.yummyuser.mapper;

import cn.edu.fudan.yummyuser.domain.Session;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ISessionMapper {
    @Insert("insert ignore into session(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSession(Session session);

    @Select("select id, name from session where id = #{id}")
    Session selectSession(Long id);
}
