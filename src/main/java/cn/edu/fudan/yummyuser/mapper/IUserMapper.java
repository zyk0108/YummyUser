package cn.edu.fudan.yummyuser.mapper;

import cn.edu.fudan.yummyuser.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserMapper {
    @Insert("insert ignore into user(email, phone_num, password) values(#{email}, #{phoneNum}, #{password})")
    int insertUser(String email, String phoneNum, String password);

    @Select("select uid, email, phone_num as phoneNum, password from user where uid = #{uid}")
    User selectUserByUID(Long uid);
}
