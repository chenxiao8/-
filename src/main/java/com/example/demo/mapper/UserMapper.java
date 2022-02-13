package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.sys.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 李昕
 * @date 2021/12/21 11:21
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username} limit 1")
    User findByUsername(String username);

    @Select("select id from user where username = #{username} limit 1")
    Integer findUserIdByName(String username);

}
