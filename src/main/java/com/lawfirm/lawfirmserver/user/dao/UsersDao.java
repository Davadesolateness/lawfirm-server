package com.lawfirm.lawfirmserver.user.dao;

import com.lawfirm.lawfirmserver.user.po.Users;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表users对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface UsersDao extends MybatisBaseDao<Users, Long> {

    Users selectUsersByUserName(String username);
}