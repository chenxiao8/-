package com.example.demo.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李昕
 * @date 2021/12/22 12:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUser {
    private String username;
    private String password;
    private Integer rememberMe;
}
