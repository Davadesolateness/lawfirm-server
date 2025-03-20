package com.lawfirm.user.dao;

import com.lawfirm.user.po.Lawyers;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表lawyers对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface LawyersDao extends MybatisBaseDao<Lawyers, Long> {

}