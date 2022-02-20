package com.example.demo.controller;

import cn.hutool.json.JSONObject;
import com.example.demo.entity.RespondResult;
import com.example.demo.entity.sys.User;
import com.example.demo.entity.vo.RegisterUserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Const;
import com.example.demo.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李昕
 * @date 2021/12/22 13:30
 * 注册控制器
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 注册接口
     * @param registerUserVO
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserVO registerUserVO){
        try {
            // 做完参数校验了,先查询一下是否该用户名的用户是否已经注册
            String username = registerUserVO.getUsername();
            User selectedUser = userMapper.findByUsername(username);
            if (selectedUser != null) {
                // 用户名被占用，无法注册
                return RespondResult.error("用户名已经存在",400);
            }else{
                // 用户名没有被占用，可以注册
                // 判断角色类型
                String role = null;
                if(registerUserVO.getType().equals(Const.ROLE_EMPLOYEE)){
                    // type为0则是求职者
                    role = Const.STRING_EMPLOYEE;
                }else if(registerUserVO.getType().equals(Const.ROLE_EMPLOYER)) {
                    // type为1则是hr
                    role = Const.STRING_EMPLOYER;
                }
                // 新建用户并且插入
                User user = new User(null,username,passwordEncoder.encode(registerUserVO.getPassword()),
                        registerUserVO.getEmail(),role);
                userMapper.insert(user);
                return RespondResult.success(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("未知错误",500);
        }
    }

    @GetMapping("/user/role")
    public ResponseEntity getRoles(){
        String userName = SecurityUtils.getUserName();
        if(userName.equals("anonymousUser")){
            return RespondResult.error("未登录",400);
        }
        User user = userMapper.findByUsername(userName);
        String role = user.getRole();
        String username = SecurityUtils.getUserName();
        JSONObject jsonObject = new JSONObject();
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("role",role);
        jsonObject.putAll(map);
        return RespondResult.success(map);
    }
}
