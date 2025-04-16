package com.lawfirm.lawfirmserver.login.dao;

import com.lawfirm.lawfirmserver.login.po.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 登录日志Mapper接口
 */
@Mapper
public interface LoginLogMapper {

    /**
     * 插入登录日志
     *
     * @param loginLog 登录日志
     * @return 影响行数
     */
    @Insert("INSERT INTO login_log(user_id, phone, login_type, status, ip, device, remark, create_time) " +
            "VALUES(#{userId}, #{phone}, #{loginType}, #{status}, #{ip}, #{device}, #{remark}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LoginLog loginLog);
} 