package com.example.demo.utils;

import com.example.demo.entity.sys.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 李昕
 * @date 2022/1/4 14:31
 */
public class SecurityUtils {

    public static String getUserName() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
