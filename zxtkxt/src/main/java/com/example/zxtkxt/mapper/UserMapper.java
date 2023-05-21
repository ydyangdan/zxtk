package com.example.zxtkxt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zxtkxt.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from sys_user where user_name = #{userName} and password = #{password} and user_role = #{userRole}")
    User findByUser(User user);

    @Insert("insert into sys_user values (null,#{userName},#{password},null,null,#{userRole},null,null,null)")
    int save(User user);

    @Select("select * from sys_user where user_name=#{userName}")
    User findByName(String name);

//    User findByPassword(User user);
}
