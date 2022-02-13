package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.DO.CVDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 李昕
 * @date 2022/2/12 17:59
 */
@Repository
public interface CVMapper extends BaseMapper<CVDO> {

    @Select("select * from cv where user_id = (select id from user where username = #{username}) limit 1")
    public CVDO findByUserName(String username);
}
