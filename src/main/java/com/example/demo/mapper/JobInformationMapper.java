package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.DO.JobInformationDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李昕
 * @date 2022/1/4 14:23
 */
@Repository
public interface JobInformationMapper  extends BaseMapper<JobInformationDO> {

    @Select("select * from job_information where user_id = #{id}")
    public List<JobInformationDO> findJobInformationByUserId(Integer id);

    @Select("select * from job_information where id = #{id}")
    public JobInformationDO findJobInformationById(Integer id);

}
