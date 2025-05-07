package com.lawfirm.lawfirmserver.lawyer.dao;

import com.lawfirm.lawfirmserver.lawyer.dto.LawyerInfoDTO;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerQueryWrapper;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表lawyer对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 */
@Mapper
public interface LawyerDao extends MybatisBaseDao<Lawyer, Long> {

    void insertSelectiveAndBackId(Lawyer lawyer);
    
    /**
     * 根据条件统计律师记录数
     *
     * @param queryWrapper 查询条件包装器
     * @return 匹配条件的记录总数
     */
    long countByCondition(@Param("query") LawyerQueryWrapper queryWrapper);
    
    /**
     * 根据条件查询律师列表
     *
     * @param queryWrapper 查询条件包装器
     * @param offset 偏移量（从0开始）
     * @param limit 查询数量限制
     * @return 律师列表
     */
    List<LawyerInfoDTO> selectByCondition(
            @Param("query") LawyerQueryWrapper queryWrapper,
            @Param("offset") int offset,
            @Param("limit") int limit);
}