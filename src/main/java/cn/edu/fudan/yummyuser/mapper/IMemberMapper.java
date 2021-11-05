package cn.edu.fudan.yummyuser.mapper;

import cn.edu.fudan.yummyuser.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IMemberMapper {
    @Insert({
            "<script>",
            "INSERT ignore INTO member (session_id, uid) ",
            "VALUES " +
                    "<foreach ='item' collection='members' open='' separator=',' close=''>" +
                    "( #{item.sessionId}, #{item.uid} )" +
                    "</foreach>",
            "</script>"
    })
    int insertMembers(List<Member> members);

    @Select("select id, session_id as sessionId, uid from member where session_id = #{sessionId}")
    List<Member> selectMembers(Long sessionId);

}
