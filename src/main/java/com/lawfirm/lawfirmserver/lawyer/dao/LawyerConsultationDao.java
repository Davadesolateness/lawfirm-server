package com.lawfirm.lawfirmserver.lawyer.dao;

import com.lawfirm.lawfirmserver.lawyer.po.LawyerConsultation;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 表lawyerconsultation对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface LawyerConsultationDao extends MybatisBaseDao<LawyerConsultation, Long> {

    /**
     * 插入咨询记录并返回自增主键ID
     * @param consultation 咨询记录对象
     * @return 影响的行数
     */
    int insertWithId(LawyerConsultation consultation);

    /**
     * 根据ID选择性更新咨询记录（只更新非空字段）
     * @param consultation 咨询记录对象，包含要更新的字段和ID
     * @return 影响的行数
     */
    int updateByIdSelective(LawyerConsultation consultation);

    /**
     * 根据主键ID查询咨询记录
     * @param id 咨询ID
     * @return 咨询记录对象
     */
    LawyerConsultation selectById(Long id);

    /**
     * 根据ID更新咨询记录（只更新非空字段）
     * @param consultation 咨询记录对象
     * @return 影响的行数
     */
    int updateById(LawyerConsultation consultation);

    /**
     * 根据律师ID查询咨询列表
     * @param lawyerId 律师ID
     * @return 咨询列表
     */
    List<LawyerConsultation> selectByLawyerId(Long lawyerId);

    /**
     * 查询待接收的咨询列表（状态为0且未分配律师）
     * @param questionType 问题类型，可为空
     * @return 咨询列表
     */
    List<LawyerConsultation> selectPendingConsultations(Long questionType);

}