package cn.edu.fudan.yummyuser.mapper;

import cn.edu.fudan.yummyuser.domain.SingleChat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ISingleChatMapper {

    @Select("select id, session_id as sessionId, uid_1 as uid1, uid_2 as uid2 from single_chat where " +
            "uid_1 = #{uid1} and uid_2 = #{uid2} for update")
    SingleChat selectSingleChatSync(Long uid1, Long uid2);

    @Insert("insert ignore into single_chat(session_id, uid_1, uid_2) values(#{sessionId}, #{uid1}, #{uid2})")
    int insertSingleChat(Long uid1, Long uid2, Long sessionId);

}
