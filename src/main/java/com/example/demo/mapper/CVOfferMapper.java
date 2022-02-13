package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.DO.CVOfferDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李昕
 * @date 2022/2/12 17:57
 */
@Repository
public interface CVOfferMapper extends BaseMapper<CVOfferDO> {

    @Select("select * from cv_offer where job_id = #{id} and user_id = (select id from user where username = #{username}) limit 1")
    public CVOfferDO findByJobIdAndUserName(Integer id,String username);

    @Select("select * from cv_offer where user_id = (select id from user where username = #{username})")
    public List<CVOfferDO> findByUserName(String username);

    @Select("select * from cv_offer where job_id = #{id}")
    public List<CVOfferDO> findByJobId(Integer id);
}
