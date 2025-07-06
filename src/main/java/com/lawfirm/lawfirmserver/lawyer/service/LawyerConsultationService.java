package com.lawfirm.lawfirmserver.lawyer.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerConsultationDao;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerConsultation;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerConsultationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.service
 * @className: LawyerConsultationService
 * @author: Eric
 * @description: 律师咨询服务类
 * @date: 2025/6/22 15:39
 * @version: 1.0
 */
@Service
@Transactional
public class LawyerConsultationService {
    
    private static final Logger log = LoggerFactory.getLogger(LawyerConsultationService.class);
    
    @Autowired
    private LawyerConsultationDao lawyerConsultationDao;
    
    /**
     * 用户提交咨询信息
     * @param consultationVo 咨询信息VO
     * @return 保存后的咨询信息
     */
    public LawyerConsultationVo submitConsultation(LawyerConsultationVo consultationVo) {
        log.info("用户提交咨询信息: userId={}, questionType={}", consultationVo.getUserId(), consultationVo.getQuestionType());
        
        // 参数校验
        if (consultationVo.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (!StringUtils.hasText(consultationVo.getConsultContent())) {
            throw new IllegalArgumentException("咨询内容不能为空");
        }
        if (consultationVo.getQuestionType() == null) {
            throw new IllegalArgumentException("问题类型不能为空");
        }
        if (!"1".equals(consultationVo.getAgreeProtocol())) {
            throw new IllegalArgumentException("必须同意咨询协议");
        }
        
        // 创建实体对象
        LawyerConsultation consultation = new LawyerConsultation();
        CommonUtil.copyProperties(consultationVo, consultation);
        
        // 设置默认值
        Date now = new Date();
        consultation.setCreateTime(now);
        consultation.setInsertTimeForHis(now);
        consultation.setOperateTimeForHis(now);
        consultation.setStatus(0); // 0：待回复
        
        // 保存到数据库，使用自定义的insertWithId方法确保返回ID
        lawyerConsultationDao.insertWithId(consultation);
        
        // 返回结果
        LawyerConsultationVo result = new LawyerConsultationVo();
        CommonUtil.copyProperties(consultation, result);
        
        log.info("用户咨询信息提交成功: id={}", consultation.getId());
        return result;
    }
    
    /**
     * 律师接收咨询
     * @param consultationId 咨询ID
     * @param lawyerId 律师ID
     * @return 更新后的咨询信息
     */
    public LawyerConsultationVo acceptConsultation(Long consultationId, Long lawyerId) {
        log.info("律师接收咨询: consultationId={}, lawyerId={}", consultationId, lawyerId);
        
        // 参数校验
        if (consultationId == null) {
            throw new IllegalArgumentException("咨询ID不能为空");
        }
        if (lawyerId == null) {
            throw new IllegalArgumentException("律师ID不能为空");
        }
        
        // 查询咨询信息
        LawyerConsultation consultation = lawyerConsultationDao.selectById(consultationId);
        if (consultation == null) {
            throw new IllegalArgumentException("咨询信息不存在");
        }
        
        // 检查咨询状态
        if (consultation.getStatus() != 0) {
            throw new IllegalArgumentException("该咨询已被处理，无法重复接收");
        }
        
        // 更新律师信息和操作时间
        consultation.setLawyerId(lawyerId);
        consultation.setOperateTimeForHis(new Date());
        
        // 更新数据库
        lawyerConsultationDao.updateById(consultation);
        
        // 返回结果
        LawyerConsultationVo result = new LawyerConsultationVo();
        CommonUtil.copyProperties(consultation, result);
        
        log.info("律师接收咨询成功: consultationId={}, lawyerId={}", consultationId, lawyerId);
        return result;
    }
    
    /**
     * 律师回复咨询
     * @param consultationId 咨询ID
     * @param lawyerId 律师ID
     * @param replyContent 回复内容
     * @return 更新后的咨询信息
     */
    public LawyerConsultationVo replyConsultation(Long consultationId, Long lawyerId, String replyContent) {
        log.info("律师回复咨询: consultationId={}, lawyerId={}", consultationId, lawyerId);
        
        // 参数校验
        if (consultationId == null) {
            throw new IllegalArgumentException("咨询ID不能为空");
        }
        if (lawyerId == null) {
            throw new IllegalArgumentException("律师ID不能为空");
        }
        if (!StringUtils.hasText(replyContent)) {
            throw new IllegalArgumentException("回复内容不能为空");
        }
        
        // 查询咨询信息
        LawyerConsultation consultation = lawyerConsultationDao.selectById(consultationId);
        if (consultation == null) {
            throw new IllegalArgumentException("咨询信息不存在");
        }
        
        // 检查是否是该律师接收的咨询
        if (!lawyerId.equals(consultation.getLawyerId())) {
            throw new IllegalArgumentException("只能回复自己接收的咨询");
        }
        
        // 检查咨询状态
        if (consultation.getStatus() == 1) {
            throw new IllegalArgumentException("该咨询已回复，无法重复回复");
        }
        if (consultation.getStatus() == 2) {
            throw new IllegalArgumentException("该咨询已关闭，无法回复");
        }
        if (consultation.getStatus() == 3) {
            throw new IllegalArgumentException("该咨询已取消，无法回复");
        }
        
        // 更新回复信息
        Date now = new Date();
        consultation.setLawyerReply(replyContent);
        consultation.setReplyTime(now);
        consultation.setStatus(1); // 1：已回复
        consultation.setOperateTimeForHis(now);
        
        // 更新数据库
        lawyerConsultationDao.updateById(consultation);
        
        // 返回结果
        LawyerConsultationVo result = new LawyerConsultationVo();
        CommonUtil.copyProperties(consultation, result);
        
        log.info("律师回复咨询成功: consultationId={}, lawyerId={}", consultationId, lawyerId);
        return result;
    }
    
    /**
     * 获取待接收的咨询列表（状态为0且未分配律师）
     * @param questionType 问题类型，可选
     * @return 咨询列表
     */
    public List<LawyerConsultationVo> getPendingConsultations(Long questionType) {
        log.info("获取待接收咨询列表: questionType={}", questionType);
        
        // 查询待接收的咨询列表
        List<LawyerConsultation> consultations = lawyerConsultationDao.selectPendingConsultations(questionType);
        
        // 转换为VO对象列表
        List<LawyerConsultationVo> result = new ArrayList<>();
        for (LawyerConsultation consultation : consultations) {
            LawyerConsultationVo vo = new LawyerConsultationVo();
            CommonUtil.copyProperties(consultation, vo);
            result.add(vo);
        }
        
        log.info("获取待接收咨询列表完成，共{}条", result.size());
        return result;
    }
    
    /**
     * 获取律师的咨询列表
     * @param lawyerId 律师ID
     * @return 咨询列表
     */
    public List<LawyerConsultationVo> getLawyerConsultations(Long lawyerId) {
        log.info("获取律师咨询列表: lawyerId={}", lawyerId);
        
        if (lawyerId == null) {
            throw new IllegalArgumentException("律师ID不能为空");
        }
        
        // 查询律师的咨询列表
        List<LawyerConsultation> consultations = lawyerConsultationDao.selectByLawyerId(lawyerId);
        
        // 转换为VO对象列表
        List<LawyerConsultationVo> result = new ArrayList<>();
        for (LawyerConsultation consultation : consultations) {
            LawyerConsultationVo vo = new LawyerConsultationVo();
            CommonUtil.copyProperties(consultation, vo);
            result.add(vo);
        }
        
        log.info("获取律师咨询列表完成，共{}条", result.size());
        return result;
    }
    
    /**
     * 根据咨询ID获取咨询详情
     * @param consultationId 咨询ID
     * @return 咨询详情
     */
    public LawyerConsultationVo getConsultationById(Long consultationId) {
        log.info("获取咨询详情: consultationId={}", consultationId);
        
        if (consultationId == null) {
            throw new IllegalArgumentException("咨询ID不能为空");
        }
        
        // 查询咨询信息
        LawyerConsultation consultation = lawyerConsultationDao.selectById(consultationId);
        if (consultation == null) {
            throw new IllegalArgumentException("咨询信息不存在");
        }
        
        // 转换为VO对象
        LawyerConsultationVo result = new LawyerConsultationVo();
        CommonUtil.copyProperties(consultation, result);
        
        log.info("获取咨询详情成功: consultationId={}", consultationId);
        return result;
    }
    
    /**
     * 更新咨询信息
     * @param consultationVo 咨询信息VO，必须包含ID
     * @return 更新后的咨询信息
     */
    public LawyerConsultationVo updateConsultation(LawyerConsultationVo consultationVo) {
        log.info("更新咨询信息: consultationId={}", consultationVo.getId());
        
        // 参数校验
        if (consultationVo.getId() == null) {
            throw new IllegalArgumentException("咨询ID不能为空");
        }
        
        // 检查咨询是否存在
        LawyerConsultation existingConsultation = lawyerConsultationDao.selectById(consultationVo.getId());
        if (existingConsultation == null) {
            throw new IllegalArgumentException("咨询信息不存在");
        }
        
        // 创建实体对象
        LawyerConsultation consultation = new LawyerConsultation();
        CommonUtil.copyProperties(consultationVo, consultation);
        
        // 更新数据库
        int result = lawyerConsultationDao.updateByIdSelective(consultation);
        if (result == 0) {
            throw new RuntimeException("更新咨询信息失败");
        }
        
        // 查询更新后的数据
        LawyerConsultation updatedConsultation = lawyerConsultationDao.selectById(consultationVo.getId());
        LawyerConsultationVo resultVo = new LawyerConsultationVo();
        CommonUtil.copyProperties(updatedConsultation, resultVo);
        
        log.info("更新咨询信息成功: consultationId={}", consultationVo.getId());
        return resultVo;
    }
}
