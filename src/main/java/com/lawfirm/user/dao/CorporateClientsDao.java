package com.lawfirm.user.dao;

import com.lawfirm.user.po.CorporateClients;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表corporate_clients对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface CorporateClientsDao extends MybatisBaseDao<CorporateClients, Long> {

}