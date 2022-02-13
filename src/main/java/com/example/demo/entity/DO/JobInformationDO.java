package com.example.demo.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李昕
 * @date 2022/1/4 14:00
 * @userId 发布的用户的id
 * @content 内容
 * @position 职位
 * @salaries 薪水
 * @frequency 工作频率
 * @period 工作时间
 * @type 招聘类型
 * @workPlace 工作地点
 */
@TableName("job_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInformationDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String content;
    private String position;
    private String salaries;
    private String frequency;
    private String period;
    private String type;
    private String workPlace;
}
