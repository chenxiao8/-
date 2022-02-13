package com.example.demo;

import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Const;
import com.example.demo.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {


    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        String ad = JwtTokenUtils.createToken("aq", Const.STRING_EMPLOYEE, true);
        System.out.println(ad);
    }

}