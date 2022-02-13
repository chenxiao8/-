package com.example.demo.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.demo.entity.DO.CVOfferDO;
import com.example.demo.entity.DO.JobInformationDO;
import com.example.demo.entity.RespondResult;
import com.example.demo.entity.sys.User;
import com.example.demo.entity.vo.JobInformationVO;
import com.example.demo.mapper.CVOfferMapper;
import com.example.demo.mapper.JobInformationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Const;
import com.example.demo.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * @author 李昕
 * @date 2022/1/4 13:51
 * hr模块控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employer")
@PreAuthorize("hasRole('ROLE_EMPLOYER')")
public class EmployerController {

    private final JobInformationMapper jobInformationMapper;
    private final UserMapper userMapper;
    private final CVOfferMapper cvOfferMapper;
    @PostMapping("/jobInformation")
    public ResponseEntity addJobInformation(@Valid @RequestBody JobInformationVO jobInformationVO){
        try {
            // 新增招聘信息,先把vo中的信息复制到DO中
            JobInformationDO jobInformationDO = new JobInformationDO();
            BeanUtils.copyProperties(jobInformationVO, jobInformationDO);
            // 获取发布招聘信息的用户id
            Integer userId = userMapper.findByUsername(SecurityUtils.getUserName()).getId();
            jobInformationDO.setUserId(userId);
            jobInformationMapper.insert(jobInformationDO);
            return RespondResult.success("成功发布招聘信息");
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("未知错误",500);
        }
    }

    @GetMapping("/jobInformation")
    public ResponseEntity findJobInformation(){
        try{
            // 首先获取当前登录用户的用户名
            String userName = SecurityUtils.getUserName();
            // 然后根据用户名查用户，再根据用户查询到发布的job信息
            Integer userId = userMapper.findUserIdByName(userName);

            List<JobInformationDO> jobInformationByUserId = jobInformationMapper.findJobInformationByUserId(userId);
            return RespondResult.success(jobInformationByUserId);
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("失败",500);
        }
    }

    /**
     * 更新指定id的招聘信息
     * @param jobId
     * @param jobInformationVO
     * @return
     */
    @PutMapping("/jobInformation/{id}")
    public ResponseEntity updateJobInformation(@PathVariable("id") Integer jobId,
                                               @Valid @RequestBody JobInformationVO
                                               jobInformationVO){
        try{
            // 首先根据id查询一下招聘信息
            JobInformationDO jobInformationById = jobInformationMapper.findJobInformationById(jobId);
            if(jobInformationById == null){
                return RespondResult.error("没有这条信息",400);
            }
            // 判断一下该条招聘信息是不是目前登录的用户发送的，如果不是，返回错误信息
            Integer userIdByName = userMapper.findUserIdByName(SecurityUtils.getUserName());
            if(!userIdByName.equals(jobInformationById.getUserId())){
                return RespondResult.error("你没有权限修改这个信息",403);
            }
            // 如果是，那么执行修改。
            BeanUtils.copyProperties(jobInformationVO,jobInformationById);
            jobInformationMapper.updateById(jobInformationById);
            return RespondResult.success("成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("失败",500);
        }
    }

    @DeleteMapping("/jobInformation/{id}")
    public ResponseEntity deleteJobInformation(@PathVariable("id") Integer id){
        try{
            // 首先根据id查询一下招聘信息
            JobInformationDO jobInformationById = jobInformationMapper.findJobInformationById(id);
            if(jobInformationById == null){
                return RespondResult.error("没有这条信息",400);
            }
            // 判断一下该条招聘信息是不是目前登录的用户发送的，如果不是，返回错误信息
            Integer userIdByName = userMapper.findUserIdByName(SecurityUtils.getUserName());
            if(!userIdByName.equals(jobInformationById.getUserId())){
                return RespondResult.error("你没有权限删除这个信息",403);
            }
            jobInformationMapper.deleteById(id);
            return RespondResult.success("成功");
            // 如果是，执行删除
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("失败",500);
        }
    }

    @GetMapping("/cvoffer/{id}")
    public ResponseEntity getCVOfferByJobId(@PathVariable("id") Integer jobId){
        try{
            // 先做参数检验，判断jobId是否属于当前登录的用户
            JobInformationDO jobInformation = jobInformationMapper.findJobInformationById(jobId);
            if(jobInformation == null){
                return RespondResult.error("没有这个招聘信息",400);
            }
            Integer userId = userMapper.findUserIdByName(SecurityUtils.getUserName());
            if(!userId.equals(jobInformation.getUserId())){
                return RespondResult.error("这不是你发布的招聘信息",400);
            }

            // 根据jobId正常查询。
            List<CVOfferDO> list = cvOfferMapper.findByJobId(jobId);
            return RespondResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("错误",500);
        }
    }

    @PatchMapping("/cvoffer/{id}")
    public ResponseEntity changeStatusByOfferId(@PathVariable("id") Integer id, @RequestBody JSONObject jsonObject){
         try{
             // 参数校验，判断这是不是属于投到自己的简历
             CVOfferDO cvOfferDO = cvOfferMapper.selectById(id);
             if(cvOfferDO == null){
                 return RespondResult.error("没有这份简历",400);
             }
             // 查找该简历对应的招聘信息
             JobInformationDO jobInformation = jobInformationMapper.findJobInformationById(cvOfferDO.getJobId());
             Integer userId = jobInformation.getUserId();
             if(!userMapper.selectById(userId).getUsername().equals(SecurityUtils.getUserName())){
                 return RespondResult.error("这不是你的招聘信息对应的简历",400);
             }
             if(cvOfferDO.getStatus().equals(Const.OFFER_PASS) || cvOfferDO.getStatus().equals(Const.OFFER_FAIL)){
                 return RespondResult.error("该简历已经不允许修改",400);
             }
             // 正式进行修改
             Boolean status = (Boolean) jsonObject.get("status");
             if(status){
                 cvOfferDO.setStatus(Const.OFFER_PASS);
             }else{
                 cvOfferDO.setStatus(Const.OFFER_FAIL);
             }
             cvOfferMapper.updateById(cvOfferDO);
             return RespondResult.success("成功");
         }catch (Exception e){
             e.printStackTrace();
             return RespondResult.error("错误",500);
         }
    }

}