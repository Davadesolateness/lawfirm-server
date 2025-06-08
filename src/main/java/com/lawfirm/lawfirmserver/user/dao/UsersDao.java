package com.lawfirm.lawfirmserver.user.dao;

import com.lawfirm.lawfirmserver.user.po.Users;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 表user对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 * @author dong
 */
@Mapper
public interface UsersDao extends MybatisBaseDao<Users, Long> {

    Users selectUserByUserName(String username);

    void insertSelectiveAndBackId(Users user);

    Users selectByPhone(String phoneNumber);

    Users selectById(Long userId);

    /**
     * 根据微信openId查询用户
     *
     * @param openId 微信openId
     * @return 用户对象
     */
    Users selectByOpenId(String openId);

    /**
     * 根据关联实体ID和用户类型查询用户
     *
     * @param relatedEntityId 关联实体ID
     * @param userType        用户类型，如lawyer、individual等
     * @return 用户对象
     */
    Users selectByRelatedEntityId(@Param("relatedEntityId") Long relatedEntityId, @Param("userType") String userType);
}