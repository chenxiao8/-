package com.example.demo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 李昕
 * @date 2022/2/12 19:25
 * 这个类是用来显示简历投递以后，求职者查看自己投递的工作是什么，以及该份工作的状态
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfferJobInformationVO {
    @NotNull
    private String content;
    @NotNull
    private String position;
    @NotNull
    private String salaries;
    @NotNull
    private String frequency;
    @NotNull
    private String period;
    @NotNull
    private String type;
    @NotNull
    private String workPlace;

    private Integer status;
}
