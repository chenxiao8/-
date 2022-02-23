package com.example.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.validation.constraints.NotNull;

/**
 * @author 李昕
 * @date 2022/1/4 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInformationVO {
    @NotNull
    private Integer id;
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
}
