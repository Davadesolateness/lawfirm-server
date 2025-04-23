package com.lawfirm.lawfirmserver.lawyer.dao;

import org.apache.ibatis.annotations.Mapper;

import com.lawfirm.lawfirmserver.lawyer.po.LawyerServiceStat;
import ins.framework.mybatis.MybatisBaseDao;

/**
 * 表lawyerservicestat对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface LawyerServiceStatDao extends MybatisBaseDao<LawyerServiceStat, Long> {

}