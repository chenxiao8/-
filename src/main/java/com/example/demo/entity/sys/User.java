package com.example.demo.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 李昕
 * @date 2021/12/21 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String hashedPassword;
    private String email;
    private String role;
}
