package com.lawfirm.lawfirmserver.lawyer.dao;

import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtyRelation;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表lawyer_specialty_relation对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface LawyerSpecialtyRelationDao extends MybatisBaseDao<LawyerSpecialtyRelation, Long> {

    List<LawyerSpecialtyRelation> selectBatchByLawyerId(Long lawyerId);

    void insertList(List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList);

    void updateList(List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList);
}