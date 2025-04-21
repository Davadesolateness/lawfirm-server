package com.lawfirm.lawfirmserver.user.dao;

import com.lawfirm.lawfirmserver.user.po.CustomerServiceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户服务信息DAO接口
 */
@Mapper
public interface CustomerServiceInfoDao {
    
    /**
     * 根据主键ID查询
     * @param id 主键ID
     * @return 客户服务信息
     */
    CustomerServiceInfo selectByPrimaryKey(Long id);
    
    /**
     * 根据用户ID查询
     * @param userId 用户ID
     * @return 客户服务信息
     */
    CustomerServiceInfo selectByUserId(Long userId);
    
    /**
     * 根据客户ID和客户类型查询
     * @param clientId 客户ID
     * @param clientType 客户类型，individual或corporate
     * @return 客户服务信息
     */
    CustomerServiceInfo selectByClientInfo(@Param("clientId") Long clientId, @Param("clientType") String clientType);
    
    /**
     * 插入记录
     * @param customerServiceInfo 客户服务信息
     * @return 影响行数
     */
    int insert(CustomerServiceInfo customerServiceInfo);
    
    /**
     * 选择性插入记录
     * @param customerServiceInfo 客户服务信息
     * @return 影响行数
     */
    int insertSelective(CustomerServiceInfo customerServiceInfo);
    
    /**
     * 选择性插入记录并返回ID
     * @param customerServiceInfo 客户服务信息
     * @return 影响行数
     */
    int insertSelectiveAndBackId(CustomerServiceInfo customerServiceInfo);
    
    /**
     * 根据主键选择性更新
     * @param customerServiceInfo 客户服务信息
     * @return 影响行数
     */
    int updateSelectiveByPrimaryKey(CustomerServiceInfo customerServiceInfo);
    
    /**
     * 根据用户ID更新
     * @param customerServiceInfo 客户服务信息
     * @return 影响行数
     */
    int updateByUserId(CustomerServiceInfo customerServiceInfo);
} 