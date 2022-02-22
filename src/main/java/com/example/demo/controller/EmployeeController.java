package com.example.demo.controller;

import cn.hutool.json.JSONObject;
import com.example.demo.entity.DO.CVDO;
import com.example.demo.entity.DO.CVOfferDO;
import com.example.demo.entity.DO.JobInformationDO;
import com.example.demo.entity.RespondResult;
import com.example.demo.entity.vo.CVVO;
import com.example.demo.entity.vo.JobInformationVO;
import com.example.demo.entity.vo.OfferJobInformationVO;
import com.example.demo.mapper.CVMapper;
import com.example.demo.mapper.CVOfferMapper;
import com.example.demo.mapper.JobInformationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Const;
import com.example.demo.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李昕
 * @date 2022/2/12 17:55
 */
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class EmployeeController {
    private final CVOfferMapper cvOfferMapper;
    private final CVMapper cvMapper;
    private final UserMapper userMapper;
    private final JobInformationMapper jobInformationMapper;

    @PostMapping("/cv")
    public ResponseEntity addCV(@Valid @RequestBody CVVO cVvo) {
        try {
            // 先查后改，用userId先查一下看看有没有重复的,一个用户现在只能拥有一份简历
            String username = SecurityUtils.getUserName();
            CVDO byUserName = cvMapper.findByUserName(username);
            if (byUserName != null) {
                // 如果已经存在简历了，那么就驳回
                return RespondResult.error("失败", 400);
            } else {
                // 不存在简历，可以正常新增
                byUserName = new CVDO();
                // 复制属性
                BeanUtils.copyProperties(cVvo, byUserName);
                Integer userId = userMapper.findUserIdByName(username);
                byUserName.setUserId(userId);
                // 插入数据
                cvMapper.insert(byUserName);
                return RespondResult.success("成功插入数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("失败", 500);
        }
    }

    @PutMapping("/cv")
    public ResponseEntity updateCV(
                                   @Valid @RequestBody CVVO cvvo) {
        try {
            String username = SecurityUtils.getUserName();
            CVDO byUserName = cvMapper.findByUserName(username);
            if (byUserName == null) {
                return RespondResult.error("简历还不存在", 400);
            }
            // 判断完成后，执行更新
            BeanUtils.copyProperties(cvvo, byUserName);
            cvMapper.updateById(byUserName);
            return RespondResult.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("失败", 500);
        }
    }

    @GetMapping("/cv")
    public ResponseEntity getCV() {
        try {
            CVDO cv = cvMapper.findByUserName(SecurityUtils.getUserName());
            CVVO cvvo = new CVVO();
            if (cv != null) {
                BeanUtils.copyProperties(cv, cvvo);
            }
            return RespondResult.success(cvvo);
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("失败", 500);
        }
    }

    @DeleteMapping("/cv/{id}")
    public ResponseEntity deleteCV(@PathVariable("id") Integer id) {
        try {
            // 首先判断这个id的简历是否属于该用户
            String username = SecurityUtils.getUserName();
            CVDO byUserName = cvMapper.findByUserName(username);
            if (byUserName == null) {
                return RespondResult.error("简历还不存在", 400);
            } else if (!byUserName.getId().equals(id)) {
                return RespondResult.error("这不是你的简历", 400);
            }
            // 判断完成后，执行更新
            cvMapper.deleteById(id);
            return RespondResult.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("失败", 500);
        }
    }

    @PostMapping("/cvoffer/{id}")
    public ResponseEntity addOfferCV(@PathVariable("id") Integer id,@RequestBody JSONObject jsonObject) {
        try {
            // 这是要投递的招聘信息的id
            //Integer id = (Integer) jsonObject.get("id");
            // 查询简历后执行投递操作.
            String userName = SecurityUtils.getUserName();
            CVDO cv = cvMapper.findByUserName(userName);
            if (cv == null) {
                return RespondResult.error("尚未新建简历", 400);
            }
            // 检查是否已经在这份工作投过简历了
            CVOfferDO byJobIdAndUserName = cvOfferMapper.findByJobIdAndUserName(id, userName);
            if (byJobIdAndUserName != null) {
                // 已经投过简历了，那么就不能再投了
                return RespondResult.error("已经投过简历了", 400);
            }
            // 投递
            CVOfferDO cvOfferDO = new CVOfferDO();
            BeanUtils.copyProperties(cv, cvOfferDO);
            cvOfferDO.setJobId(id);
            cvOfferDO.setStatus(Const.OFFER_WAIT);
            cvOfferMapper.insert(cvOfferDO);
            return RespondResult.success("投递成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("错误", 500);
        }
    }

    @GetMapping("/jobInformation")
    public ResponseEntity getJobInformation() {
        try {
            // 得到所有的招聘信息。遍历数据库元素即可。
            List<JobInformationDO> jobInformationDOS = jobInformationMapper.selectList(null);
            List<JobInformationVO> jobInformationVOS = new ArrayList<>();
            for (JobInformationDO jobInformationDO : jobInformationDOS) {
                JobInformationVO jobInformationVO = new JobInformationVO();
                BeanUtils.copyProperties(jobInformationDO, jobInformationVO);
                jobInformationVOS.add(jobInformationVO);
            }
            return RespondResult.success(jobInformationVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return RespondResult.error("错误", 500);
        }
    }

    @GetMapping("/myJobInformation")
    public ResponseEntity getOfferJobInformation(){
        try{
            // 先查cv_offer中属于自己username的部分
            String username = SecurityUtils.getUserName();
            List<CVOfferDO> cvoffers = cvOfferMapper.findByUserName(username);

            // 新建list，对每一个cvoffer新建offerJobInformationVO
            List<OfferJobInformationVO> offerJobInformationVOS = new ArrayList<>();
            for(CVOfferDO cvOfferDO:cvoffers){
                // 根据jobId查询工作信息
                JobInformationDO jobInformationById = jobInformationMapper.findJobInformationById(cvOfferDO.getJobId());
                OfferJobInformationVO offerJobInformationVO = new OfferJobInformationVO();
                // 复制工作信息
                BeanUtils.copyProperties(jobInformationById,offerJobInformationVO);
                // 复制状态
                offerJobInformationVO.setStatus(cvOfferDO.getStatus());
                offerJobInformationVOS.add(offerJobInformationVO);
            }
            return RespondResult.success(offerJobInformationVOS);
        }catch (Exception e){
            e.printStackTrace();
            return RespondResult.error("错误",500);
        }
    }
}
