package com.lawfirm.lawfirmserver.login.dao;

import com.lawfirm.lawfirmserver.login.po.LoginLog;
import com.lawfirm.lawfirmserver.login.po.SmsVerification;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 登录日志Mapper接口
 */
@Mapper
public interface LoginLogDao extends MybatisBaseDao<LoginLog, Long> {

    /**
     * 插入登录日志
     *
     * @param loginLog 登录日志对象
     * @return 影响的行数
     */
    int insertLoginLog(LoginLog loginLog);
}