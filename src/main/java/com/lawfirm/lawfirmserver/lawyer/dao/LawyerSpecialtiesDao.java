package com.lawfirm.lawfirmserver.lawyer.dao;

import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialties;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表lawyer_specialties对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface LawyerSpecialtiesDao extends MybatisBaseDao<LawyerSpecialties, Long> {

}