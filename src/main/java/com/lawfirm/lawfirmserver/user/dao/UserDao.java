package com.lawfirm.lawfirmserver.user.dao;

import com.lawfirm.lawfirmserver.user.po.User;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表user对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface UserDao extends MybatisBaseDao<User, Long> {

    User selectUserByUserName(String username);

    void insertSelectiveAndBackId(User user);

    User selectByPhone(String phoneNumber);

    User selectById(Long userId);
}