package com.lawfirm.lawfirmserver.lawyer.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.lawyer.vo.ConsultationAcceptVo;
import com.lawfirm.lawfirmserver.lawyer.vo.ConsultationReplyVo;
import com.lawfirm.lawfirmserver.lawyer.vo.ConsultationSubmitVo;
import com.lawfirm.lawfirmserver.lawyer.vo.ConsultationUpdateVo;
import com.lawfirm.lawfirmserver.lawyer.service.LawyerConsultationService;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerConsultationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.lawyer.api
 * @className: LawyerConsultationApi
 * @author: Eric
 * @description: 律师咨询API控制器
 * @date: 2025/6/22 15:39
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/lawyerConsultation")
@Api(tags = "律师咨询管理接口", description = "提供律师咨询相关的服务接口")
public class LawyerConsultationApi {

    @Autowired
    private LawyerConsultationService lawyerConsultationService;

    /**
     * 用户提交咨询信息
     *
     * @param consultationSubmitVo 咨询提交请求
     * @param httpRequest          HTTP请求对象
     * @return 提交结果
     */
    @ApiOperation(value = "用户提交咨询信息", notes = "用户填写咨询内容后提交，系统保存咨询信息")
    @PostMapping("/submitConsultation")
    public Result<LawyerConsultationVo> submitConsultation(
            @RequestBody @Valid ConsultationSubmitVo consultationSubmitVo, HttpServletRequest httpRequest) {
        try {
            log.info("用户提交咨询信息: userId={}, questionType={}", consultationSubmitVo.getUserId(), consultationSubmitVo.getQuestionType());

            // 转换为VO对象
            LawyerConsultationVo consultationVo = new LawyerConsultationVo();
            BeanUtils.copyProperties(consultationSubmitVo, consultationVo);

            // 调用服务层处理
            LawyerConsultationVo result = lawyerConsultationService.submitConsultation(consultationVo);

            log.info("用户咨询信息提交成功: consultationId={}", result.getId());
            return Result.success("咨询信息提交成功", result);

        } catch (IllegalArgumentException e) {
            log.warn("用户咨询信息提交失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("用户咨询信息提交失败: ", e);
            return Result.fail("咨询信息提交失败: " + e.getMessage());
        }
    }

    /**
     * 律师接收咨询
     *
     * @param request     接收咨询请求
     * @param httpRequest HTTP请求对象
     * @return 接收结果
     */
    @ApiOperation(value = "律师接收咨询", notes = "律师选择并接收用户的咨询信息")
    @PostMapping("/acceptConsultation")
    public Result<LawyerConsultationVo> acceptConsultation(
            @RequestBody @Valid ConsultationAcceptVo request,
            HttpServletRequest httpRequest) {
        try {
            log.info("律师接收咨询: consultationId={}, lawyerId={}", request.getConsultationId(), request.getLawyerId());

            // 调用服务层处理
            LawyerConsultationVo result = lawyerConsultationService.acceptConsultation(
                    request.getConsultationId(), request.getLawyerId());

            log.info("律师接收咨询成功: consultationId={}, lawyerId={}",
                    request.getConsultationId(), request.getLawyerId());
            return Result.success("咨询接收成功", result);

        } catch (IllegalArgumentException e) {
            log.warn("律师接收咨询失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("律师接收咨询失败: ", e);
            return Result.fail("咨询接收失败: " + e.getMessage());
        }
    }

    /**
     * 律师回复咨询
     *
     * @param request     回复咨询请求
     * @param httpRequest HTTP请求对象
     * @return 回复结果
     */
    @ApiOperation(value = "律师回复咨询", notes = "律师针对已接收的咨询提供专业回复")
    @PostMapping("/replyConsultation")
    public Result<LawyerConsultationVo> replyConsultation(
            @RequestBody @Valid ConsultationReplyVo request,
            HttpServletRequest httpRequest) {
        try {
            log.info("律师回复咨询: consultationId={}, lawyerId={}", request.getConsultationId(), request.getLawyerId());

            // 调用服务层处理
            LawyerConsultationVo result = lawyerConsultationService.replyConsultation(
                    request.getConsultationId(), request.getLawyerId(), request.getReplyContent());

            log.info("律师回复咨询成功: consultationId={}, lawyerId={}",
                    request.getConsultationId(), request.getLawyerId());
            return Result.success("咨询回复成功", result);

        } catch (IllegalArgumentException e) {
            log.warn("律师回复咨询失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("律师回复咨询失败: ", e);
            return Result.fail("咨询回复失败: " + e.getMessage());
        }
    }

    /**
     * 获取待接收的咨询列表
     *
     * @param questionType 问题类型，可选
     * @return 咨询列表
     */
    @ApiOperation(value = "获取待接收咨询列表", notes = "获取所有待接收的咨询信息，律师可以选择接收")
    @GetMapping("/getPendingConsultations")
    public Result<List<LawyerConsultationVo>> getPendingConsultations(
            @ApiParam(value = "问题类型", example = "1")
            @RequestParam(value = "questionType", required = false) Long questionType) {
        try {
            log.info("获取待接收咨询列表: questionType={}", questionType);

            List<LawyerConsultationVo> result = lawyerConsultationService.getPendingConsultations(questionType);

            log.info("获取待接收咨询列表成功，共{}条", result.size());
            return Result.success(result);

        } catch (Exception e) {
            log.error("获取待接收咨询列表失败: ", e);
            return Result.fail("获取咨询列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取律师的咨询列表
     *
     * @param lawyerId 律师ID
     * @return 律师的咨询列表
     */
    @ApiOperation(value = "获取律师咨询列表", notes = "获取指定律师已接收的所有咨询信息")
    @GetMapping("/getLawyerConsultations")
    public Result<List<LawyerConsultationVo>> getLawyerConsultations(
            @ApiParam(value = "律师ID", required = true, example = "1")
            @RequestParam(value = "lawyerId") Long lawyerId) {
        try {
            log.info("获取律师咨询列表: lawyerId={}", lawyerId);

            List<LawyerConsultationVo> result = lawyerConsultationService.getLawyerConsultations(lawyerId);

            log.info("获取律师咨询列表成功，共{}条", result.size());
            return Result.success(result);

        } catch (IllegalArgumentException e) {
            log.warn("获取律师咨询列表失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取律师咨询列表失败: ", e);
            return Result.fail("获取咨询列表失败: " + e.getMessage());
        }
    }

        /**
     * 根据咨询ID获取咨询详情
     * 
     * @param consultationId 咨询ID
     * @return 咨询详情
     */
    @ApiOperation(value = "获取咨询详情", notes = "根据咨询ID获取具体的咨询信息")
    @GetMapping("/getConsultationById")
    public Result<LawyerConsultationVo> getConsultationById(
            @ApiParam(value = "咨询ID", required = true, example = "1") 
            @RequestParam(value = "consultationId") Long consultationId) {
        try {
            log.info("获取咨询详情: consultationId={}", consultationId);
            
            if (consultationId == null) {
                return Result.fail("咨询ID不能为空");
            }
            
            // 调用服务层获取咨询详情
            LawyerConsultationVo result = lawyerConsultationService.getConsultationById(consultationId);
            
            log.info("获取咨询详情成功: consultationId={}", consultationId);
            return Result.success(result);
            
        } catch (Exception e) {
            log.error("获取咨询详情失败: ", e);
            return Result.fail("获取咨询详情失败: " + e.getMessage());
        }
    }

    /**
     * 更新咨询信息
     * 
     * @param request 更新咨询请求
     * @param httpRequest HTTP请求对象
     * @return 更新结果
     */
    @ApiOperation(value = "更新咨询信息", notes = "根据咨询ID更新咨询信息，只更新传入的非空字段")
    @PostMapping("/updateConsultation")
    public Result<LawyerConsultationVo> updateConsultation(
            @RequestBody @Valid ConsultationUpdateVo request, 
            HttpServletRequest httpRequest) {
        try {
            log.info("更新咨询信息: consultationId={}", request.getId());
            
            // 转换为服务层需要的VO对象
            LawyerConsultationVo consultationVo = new LawyerConsultationVo();
            BeanUtils.copyProperties(request, consultationVo);
            
            // 调用服务层处理
            LawyerConsultationVo result = lawyerConsultationService.updateConsultation(consultationVo);
            
            log.info("更新咨询信息成功: consultationId={}", request.getId());
            return Result.success("咨询信息更新成功", result);
            
        } catch (IllegalArgumentException e) {
            log.warn("更新咨询信息失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("更新咨询信息失败: ", e);
            return Result.fail("咨询信息更新失败: " + e.getMessage());
        }
    }
}
