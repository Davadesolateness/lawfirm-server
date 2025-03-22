package com.lawfirm.lawfirmserver.user.dao;

import com.lawfirm.lawfirmserver.user.po.IndividualClient;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表individual_clients对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface IndividualClientDao extends MybatisBaseDao<IndividualClient, Long> {

    void insertSelectiveAndBackId(IndividualClient individualClients);
}