package com.lawfirm.lawfirmserver.common.dao;

import com.lawfirm.lawfirmserver.common.entity.CodeInfo;
import ins.framework.mybatis.MybatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统编码信息数据访问对象
 * 对应数据库表：codeInfo
 * 用于处理省市区等地区编码信息的数据库操作
 */
@Mapper
public interface CodeInfoDao extends MybatisBaseDao<CodeInfo, Long> {

    /**
     * 根据编码代码查询编码信息
     *
     * @param codeCode 编码代码
     * @return 编码信息对象，如果不存在则返回null
     */
    CodeInfo selectByCodeCode(@Param("codeCode") String codeCode);

    /**
     * 根据编码类型查询编码信息列表
     *
     * @param codeType 编码类型，如AreaCode
     * @return 编码信息列表
     */
    List<CodeInfo> selectByCodeType(@Param("codeType") String codeType);

    /**
     * 根据上级编码查询下级编码信息列表
     *
     * @param upperCode 上级编码
     * @return 编码信息列表
     */
    List<CodeInfo> selectByUpperCode(@Param("upperCode") String upperCode);

    /**
     * 根据等级和上级编码查询编码信息列表
     *
     * @param level     等级，如省级、市级等
     * @param upperCode 上级编码
     * @return 编码信息列表
     */
    List<CodeInfo> selectByLevelAndUpperCode(@Param("level") String level,
                                             @Param("upperCode") String upperCode);

    /**
     * 根据编码代码列表批量查询编码信息
     *
     * @param codeCodes 编码代码列表
     * @return 编码信息列表
     */
    List<CodeInfo> selectByCodeCodes(@Param("codeCodes") List<String> codeCodes);

    /**
     * 根据编码类型和等级查询编码信息列表
     *
     * @param codeType 编码类型
     * @param level    等级
     * @return 编码信息列表
     */
    List<CodeInfo> selectByCodeTypeAndLevel(@Param("codeType") String codeType,
                                            @Param("level") String level);
} 