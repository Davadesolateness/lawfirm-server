package com.lawfirm.lawfirmserver.admin.service;

import com.lawfirm.lawfirmserver.admin.dao.AdministratorDao;
import com.lawfirm.lawfirmserver.admin.po.Administrator;
import com.lawfirm.lawfirmserver.admin.vo.AdministratorVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员服务实现类
 */
@Service
public class AdminService {

    @Autowired
    private AdministratorDao administratorDao;

    /**
     * 根据管理员ID获取管理员详细信息
     * @param id 管理员ID
     * @return 管理员视图对象（包含用户和图片信息）
     */
    public AdministratorVo getAdministratorById(String id) {
        // 直接查询包含用户和图片信息的管理员VO对象
        AdministratorVo administratorVo = administratorDao.selectAdministratorById(id);
        
        // 如果未找到管理员信息，返回null
        if (administratorVo == null) {
            return null;
        }
        
        return administratorVo;
    }
    
    /**
     * 根据管理员用户名获取管理员详细信息
     * @param adminName 管理员用户名
     * @return 管理员视图对象
     */
    public AdministratorVo getAdministratorByName(String adminName) {
        // 查询管理员信息
        Administrator administrator = administratorDao.selectAdministratorByName(adminName);
        
        // 如果未找到管理员信息，返回null
        if (administrator == null) {
            return null;
        }
        
        // 转换为VO对象
        AdministratorVo administratorVo = new AdministratorVo();
        BeanUtils.copyProperties(administrator, administratorVo);
        
        return administratorVo;
    }
    
    /**
     * 更新管理员信息
     * @param administrator 管理员持久化对象
     * @return 是否更新成功
     */
    public boolean updateAdministratorInfo(Administrator administrator) {
        // 更新管理员信息
        return administratorDao.updateSelectiveByPrimaryKey(administrator) > 0;
    }
    
    /**
     * 创建新管理员
     * @param administrator 管理员持久化对象
     * @return 新创建的管理员ID
     */
    public Long createAdministrator(Administrator administrator) {
        // 插入管理员信息并返回ID
        administratorDao.insertSelectiveAndBackId(administrator);
        return administrator.getId();
    }
} 