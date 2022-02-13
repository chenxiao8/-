package com.example.demo.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李昕
 * @date 2022/2/12 15:56
 */
@TableName("cv_offer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CVOfferDO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer jobId;
    private Integer status;
    private String name;
    private String email;
    private String phone;
    private String nowLocation;
    private String education;
    private String workExperience;
    private String schoolExperience;
    private String jobExperience;
    private String selfIntroduction;
}
