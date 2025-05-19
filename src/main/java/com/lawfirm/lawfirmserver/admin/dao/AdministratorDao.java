package com.lawfirm.lawfirmserver.admin.dao;

import com.lawfirm.lawfirmserver.admin.po.Administrator;
import com.lawfirm.lawfirmserver.admin.vo.AdministratorDetailVo;
import com.lawfirm.lawfirmserver.admin.vo.AdministratorVo;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员数据访问接口
 */
@Mapper
public interface AdministratorDao extends MybatisBaseDao<Administrator, Long> {
    /**
     * 根据管理员ID查询管理员详细信息（包含用户和图片信息）
     *
     * @param id 管理员ID
     * @return 管理员视图对象
     */
    AdministratorDetailVo selectAdministratorById(@Param("id") String id);

    /**
     * 根据管理员用户名查询管理员详细信息
     *
     * @param adminName 管理员用户名
     * @return 管理员信息
     */
    Administrator selectAdministratorByName(@Param("adminName") String adminName);
    
    /**
     * 选择性插入记录并返回ID
     *
     * @param administrator 管理员信息
     * @return 影响行数
     */
    int insertSelectiveAndBackId(Administrator administrator);
} 