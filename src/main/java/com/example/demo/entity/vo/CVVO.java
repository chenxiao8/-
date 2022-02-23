package com.example.demo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author 李昕
 * @date 2022/2/12 18:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CVVO {
    @NotNull
    Integer id;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String nowLocation;
    @NotNull
    private String education;
    @NotNull
    private String workExperience;
    @NotNull
    private String schoolExperience;
    @NotNull
    private String jobExperience;
    @NotNull
    private String selfIntroduction;
}
