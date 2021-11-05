package cn.edu.fudan.yummyuser.mapper;

import cn.edu.fudan.yummyuser.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IMessageMapper {

    @Insert({
            "<script>",
            "INSERT ignore INTO message (session_id, uid, seq_id, raw) ",
            "VALUES " +
                    "<foreach ='item' collection='messages' open='' separator=',' close=''>" +
                    "( #{item.sessionId}, #{item.uid}, #{item.seqId}, #{item.raw} )" +
                    "</foreach>",
            "</script>"
    })
    int insertMessages(List<Message> messages);

    @Select("select id, session_id as sessionId, uid, seq_id as seqId, raw, created_at as createdAt from message " +
            "where session_id = #{sessionId} and id <= #{lastId} order by id desc limit #{limit}")
    List<Message> selectMessages(Long sessionId, Long lastId, int limit);

}
