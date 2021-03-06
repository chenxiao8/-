package com.example.demo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * @author 李昕
 * @date 2021/12/30 21:17
 * @type 如果type为1则是HR，如果type为0则是求职者
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserVO {

    @NotNull(message = "用户名不能为空")
    private String username;

    @Email(message = "邮箱格式错误")
    private String email;
    @Length(min = 6, max = 14, message = "密码长度必须为6-14")
    @NotNull
    private String password;

    @Min(value = 0, message = "用户类型错误")
    @Max(value = 1, message = "用户类型错误")
    private Integer type;
}
