package com.lawfirm.lawfirmserver.login.dao;

import com.lawfirm.lawfirmserver.login.po.SmsVerification;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author dongzhibo
 * @version 1.0
 * @description: 短信验证登录dao层
 * @date 2025/4/15 21:08
 */
@Mapper
public interface SmsVerificationDao extends MybatisBaseDao<SmsVerification, Long> {
    SmsVerification selectLatestByPhoneAndType(@Param("phone") String phone, @Param("type") Integer type);

}
