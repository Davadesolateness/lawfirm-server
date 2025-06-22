package com.lawfirm.lawfirmserver.lawyer.service;

import com.lawfirm.lawfirmserver.common.PageResult;
import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.common.dao.CodeInfoDao;
import com.lawfirm.lawfirmserver.common.po.CodeInfo;
import com.lawfirm.lawfirmserver.image.dao.ImageStorageDao;
import com.lawfirm.lawfirmserver.image.po.ImageStorage;
import com.lawfirm.lawfirmserver.image.util.ImageUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyRelationDao;
import com.lawfirm.lawfirmserver.lawyer.dto.LawyerInfoDTO;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialty;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtyRelation;
import com.lawfirm.lawfirmserver.lawyer.vo.*;
import com.lawfirm.lawfirmserver.user.dao.UsersDao;
import com.lawfirm.lawfirmserver.user.po.Users;
import ins.framework.mybatis.Page;
import ins.framework.mybatis.PageParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LawyerService {
    private static final Logger log = LoggerFactory.getLogger(LawyerService.class);


    @Autowired
    LawyerDao lawyerDao;
    @Autowired
    LawyerSpecialtyRelationDao lawyerSpecialtyRelationDao;
    @Autowired
    LawyerSpecialtyDao lawyerSpecialtyDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    ImageStorageDao imageStorageDao;

    @Autowired
    CodeInfoDao codeInfoDao;

    /**
     * 更新律师信息及其关联的专长信息
     *
     * @param lawyerVo 包含律师信息和专长关联信息的视图对象
     */
    public LawyerVo updateLawyer(LawyerVo lawyerVo) {
        // 创建 Lawyer 对象并复制律师信息
        Lawyer lawyer = new Lawyer();
        CommonUtil.copyProperties(lawyerVo, lawyer);
        // 根据主键选择性更新律师信息
        lawyerDao.updateSelectiveByPrimaryKey(lawyer);

        // 检查专长关联信息列表是否为空
        if (lawyerVo.getLawyerSpecialtyRelationVolist() != null && !lawyerVo.getLawyerSpecialtyRelationVolist().isEmpty()) {
            // 存储需插入、更新、删除的专长关联信息
            List<LawyerSpecialtyRelation> insertList = new ArrayList<>();
            List<LawyerSpecialtyRelation> updateList = new ArrayList<>();
            List<Long> deleteList = new ArrayList<>();

            // 存储从数据库查询的专长关联信息
            HashMap<Long, LawyerSpecialtyRelation> poIdMap = new HashMap<>();
            HashMap<Long, LawyerSpecialtyRelation> poIdSaveMap = new HashMap<>();
            HashMap<Long, LawyerSpecialtyRelation> pospecialtyIdMap = new HashMap<>();
            // 查询该律师的所有专长关联信息
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyer.getId());
            for (LawyerSpecialtyRelation lsrPo : lawyerSpecialtyRelationList) {
                poIdMap.put(lsrPo.getId(), lsrPo);
                pospecialtyIdMap.put(lsrPo.getSpecialtyId(), lsrPo);
            }

            // 遍历专长关联信息，分类添加到对应列表
            for (LawyerSpecialtyRelationVo lsrVo : lawyerVo.getLawyerSpecialtyRelationVolist()) {
                LawyerSpecialtyRelation lsr = new LawyerSpecialtyRelation();
                if (lsrVo.getId() == null) {
                    if (!pospecialtyIdMap.containsKey(lsrVo.getSpecialtyId())) {
                        // 新增专长关联信息
                        CommonUtil.copyProperties(lsrVo, lsr);
                        insertList.add(lsr);
                    } else {
                        // 已有专长关联信息，更新
                        CommonUtil.copyProperties(lsrVo, lsr);
                        lsr.setId(pospecialtyIdMap.get(lsrVo.getSpecialtyId()).getId());
                        poIdSaveMap.put(lsr.getId(), lsr);
                        updateList.add(lsr);
                    }
                } else if (poIdMap.containsKey(lsrVo.getId())) {
                    // 已有专长关联信息，更新
                    CommonUtil.copyProperties(lsrVo, lsr);
                    lsr.setId(poIdMap.get(lsrVo.getId()).getId());
                    poIdSaveMap.put(lsr.getId(), lsr);
                    updateList.add(lsr);
                }
            }

            // 找出需要删除的专长关联信息
            for (LawyerSpecialtyRelation lsrPo : lawyerSpecialtyRelationList) {
                if (!poIdSaveMap.containsKey(lsrPo.getId())) {
                    deleteList.add(lsrPo.getId());
                }
            }

            // 批量插入、更新、删除专长关联信息
            if (!insertList.isEmpty()) {
                lawyerSpecialtyRelationDao.insertList(insertList);
            }
            if (!updateList.isEmpty()) {
                lawyerSpecialtyRelationDao.updateList(updateList);
            }
            if (!deleteList.isEmpty()) {
                lawyerSpecialtyRelationDao.deleteBatchByPrimaryKeys(deleteList);
            }
        }

        return lawyerVo;
    }

    /**
     * 根据律师 ID 获取律师信息及其关联专长信息，并加载头像
     *
     * @param lawyerId 律师 ID
     * @return 封装律师信息和专长信息的 LawyerVo 对象
     */
    public LawyerVo getLawyer(Long lawyerId) {
        // 初始化 LawyerVo 对象和专长关联信息列表
        LawyerVo lawyerVo = new LawyerVo();
        List<LawyerSpecialtyRelationVo> lawyerSpecialtyRelationVoList = new ArrayList<>();
        List<String> specialtyNamesList = new ArrayList<>();

        // 查询律师基本信息
        Lawyer lawyer = lawyerDao.selectByPrimaryKey(lawyerId);

        // 查询律师的专长关联信息
        List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyer.getId());

        // 处理专长关联信息
        if (lawyerSpecialtyRelationList != null && !lawyerSpecialtyRelationList.isEmpty()) {
            for (LawyerSpecialtyRelation relation : lawyerSpecialtyRelationList) {
                // 创建专长关联信息视图对象和专长信息视图对象
                LawyerSpecialtyRelationVo relationVo = new LawyerSpecialtyRelationVo();
                LawyerSpecialtyVo specialtyVo = new LawyerSpecialtyVo();

                // 查询专长详细信息
                LawyerSpecialty specialty = lawyerSpecialtyDao.selectByPrimaryKey(relation.getSpecialtyId());

                // 复制信息到视图对象
                CommonUtil.copyProperties(specialty, specialtyVo);
                CommonUtil.copyProperties(relation, relationVo);

                // 关联专长信息到专长关联信息视图对象
                relationVo.setLawyerSpecialtyVo(specialtyVo);
                // 添加到专长关联信息视图对象列表
                lawyerSpecialtyRelationVoList.add(relationVo);

                // 提取专长名称添加到specialtyNamesList列表
                if (specialty.getSpecialtyName() != null && !specialty.getSpecialtyName().isEmpty()) {
                    specialtyNamesList.add(specialty.getSpecialtyName());
                }
            }
        }

        // 复制律师基本信息到 LawyerVo 对象
        CommonUtil.copyProperties(lawyer, lawyerVo);
        // 设置专长关联信息视图对象列表到 LawyerVo 对象
        lawyerVo.setLawyerSpecialtyRelationVolist(lawyerSpecialtyRelationVoList);
        // 设置专长名称逗号分隔字符串
        lawyerVo.setSpecialtyNames(String.join(",", specialtyNamesList));

        // 查询关联的用户信息和头像
        fetchUserInfoAndAvatar(lawyerId, lawyerVo);

        // 翻译地址信息
        translateAddress(lawyerVo);

        return lawyerVo;
    }

    /**
     * 翻译律师地址信息
     * 根据律师的provinceCode、cityCode、districtCode通过codeInfo表翻译为中文地址
     *
     * @param lawyerVo 律师VO对象
     */
    private void translateAddress(LawyerVo lawyerVo) {
        try {
            StringBuilder addressBuilder = new StringBuilder();
            
            // 查询省份信息（省份代码后补4个零）
            if (lawyerVo.getProvinceCode() != null && !lawyerVo.getProvinceCode().isEmpty()) {
                String provinceCodeForQuery = lawyerVo.getProvinceCode();
                // 如果不是6位数，则补零到6位
                if (provinceCodeForQuery.length() < 6) {
                    provinceCodeForQuery = provinceCodeForQuery + "0000".substring(0, 6 - provinceCodeForQuery.length());
                }
                
                CodeInfo provinceInfo = codeInfoDao.selectByCodeCode(provinceCodeForQuery);
                if (provinceInfo != null) {
                    addressBuilder.append(provinceInfo.getCodeName());
                    log.info("省份信息查询成功: {} -> {} -> {}", lawyerVo.getProvinceCode(), provinceCodeForQuery, provinceInfo.getCodeName());
                } else {
                    log.warn("未找到省份信息: {} (查询代码: {})", lawyerVo.getProvinceCode(), provinceCodeForQuery);
                }
            }
            
            // 查询城市信息（城市代码后补2个零）
            if (lawyerVo.getCityCode() != null && !lawyerVo.getCityCode().isEmpty()) {
                String cityCodeForQuery = lawyerVo.getCityCode();
                // 如果不是6位数，则补零到6位
                if (cityCodeForQuery.length() < 6) {
                    cityCodeForQuery = cityCodeForQuery + "00".substring(0, 6 - cityCodeForQuery.length());
                }
                
                CodeInfo cityInfo = codeInfoDao.selectByCodeCode(cityCodeForQuery);
                if (cityInfo != null) {
                    if (addressBuilder.length() > 0) {
                        addressBuilder.append(" ");
                    }
                    addressBuilder.append(cityInfo.getCodeName());
                    log.info("城市信息查询成功: {} -> {} -> {}", lawyerVo.getCityCode(), cityCodeForQuery, cityInfo.getCodeName());
                } else {
                    log.warn("未找到城市信息: {} (查询代码: {})", lawyerVo.getCityCode(), cityCodeForQuery);
                }
            }
            
            // 查询区县信息（区县代码保持原样，应该已经是6位）
            if (lawyerVo.getDistrictCode() != null && !lawyerVo.getDistrictCode().isEmpty()) {
                CodeInfo districtInfo = codeInfoDao.selectByCodeCode(lawyerVo.getDistrictCode());
                if (districtInfo != null) {
                    if (addressBuilder.length() > 0) {
                        addressBuilder.append(" ");
                    }
                    addressBuilder.append(districtInfo.getCodeName());
                    log.info("区县信息查询成功: {} -> {}", lawyerVo.getDistrictCode(), districtInfo.getCodeName());
                } else {
                    log.warn("未找到区县信息: {}", lawyerVo.getDistrictCode());
                }
            }
            
            // 设置完整地址
            String fullAddress = addressBuilder.toString().trim();
            lawyerVo.setAddress(fullAddress.isEmpty() ? "地址信息不完整" : fullAddress);
            log.info("地址转换完成: {}", lawyerVo.getAddress());
            
        } catch (Exception e) {
            log.error("地址转换失败: {}", e.getMessage(), e);
            lawyerVo.setAddress("地址解析失败");
        }
    }

    /**
     * 根据律师ID查询关联的用户信息和头像
     *
     * @param lawyerId 律师ID
     * @param lawyerVo 律师VO对象，用于设置用户信息和头像
     */
    private void fetchUserInfoAndAvatar(Long lawyerId, LawyerVo lawyerVo) {
        try {
            // 1. 根据律师ID从用户表中查询关联用户
            Users user = findUserByLawyerId(lawyerId);

            if (user != null) {
                // 设置关联的用户ID
                lawyerVo.setUserId(user.getId());

                // 设置律师电话号码
                if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                    lawyerVo.setPhone(user.getPhoneNumber());
                    log.info("律师ID: {} 的电话号码设置为: {}", lawyerId, user.getPhoneNumber());
                } else {
                    log.warn("律师ID: {} 的关联用户电话号码为空", lawyerId);
                }

                // 2. 查询用户的头像
                ImageStorage avatar = imageStorageDao.selectLatestAvatarByUserId(user.getId());

                if (avatar != null && avatar.getImageData() != null) {
                    // 转换为Base64字符串
                    String base64Image = Base64.encodeBase64String(avatar.getImageData());
                    lawyerVo.setImageData(base64Image);

                    // 设置图片类型
                    String imageType = ImageUtil.getMimeTypeFromExtension(avatar.getFileExtension());
                    lawyerVo.setImageType(imageType);
                }
            } else {
                log.warn("未找到律师ID: {} 的关联用户信息", lawyerId);
            }
        } catch (Exception e) {
            // 记录异常但不影响主功能
            System.err.println("获取律师用户信息和头像失败: " + e.getMessage());
            log.error("获取律师用户信息和头像失败: " + e.getMessage(), e);
        }
    }

    /**
     * 通过律师ID查找关联的用户
     *
     * @param lawyerId 律师ID
     * @return 关联的用户对象，如果没有则返回null
     */
    private Users findUserByLawyerId(Long lawyerId) {
        try {
            // 使用新增的DAO方法通过关联实体ID和用户类型查询
            return usersDao.selectByRelatedEntityId(lawyerId, "lawyer");
        } catch (Exception e) {
            System.err.println("通过律师ID查找用户失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * 多维度筛选查询律师
//     *
//     * @param queryDTO 查询条件
//     * @return 分页的律师信息列表
//     */
//    public Page<LawyerVo> searchLawyers(LawyerQueryDTO queryDTO) {
//        // 创建查询条件对象
//        Lawyer queryCondition = new Lawyer();
//
//        // 设置查询条件
//        if (StringUtils.hasText(queryDTO.getField())) {
//            queryCondition.setField(queryDTO.getField());
//        }
//        if (StringUtils.hasText(queryDTO.getRegion())) {
//            queryCondition.setRegion(queryDTO.getRegion());
//        }
//
//        // 创建分页参数对象
//        PageParam pageParam = new PageParam();
//        pageParam.setPageNo(queryDTO.getPageNum());
//        pageParam.setPageSize(queryDTO.getPageSize());
//
//        // 添加排序条件
//        if (queryDTO.getMinExperienceYears() != null || queryDTO.getMaxExperienceYears() != null) {
//            pageParam.addCondition("experienceYears >= ", queryDTO.getMinExperienceYears());
//            pageParam.addCondition("experienceYears <= ", queryDTO.getMaxExperienceYears());
//        }
//
//        if (queryDTO.getMinRating() != null || queryDTO.getMaxRating() != null) {
//            pageParam.addCondition("rating >= ", queryDTO.getMinRating());
//            pageParam.addCondition("rating <= ", queryDTO.getMaxRating());
//        }
//
//        // 设置默认排序
//        pageParam.setOrderBy("rating DESC");
//
//        // 执行分页查询
//        Page<Lawyer> lawyerPage = lawyerDao.selectPage(pageParam, queryCondition);
//
//        // 转换结果为VO对象
//        Page<LawyerVo> resultPage = new Page<>(lawyerPage.getPaginator());
//        for (Lawyer lawyer : lawyerPage) {
//            LawyerVo vo = new LawyerVo();
//            CommonUtil.copyProperties(lawyer, vo);
//            resultPage.add(vo);
//        }
//
//        return resultPage;
//    }

    /**
     * 根据律师 ID 获取律师信息及其关联专长信息
     *
     * @param lawyerId 律师 ID
     * @return 封装律师信息和专长信息的 LawyerVo 对象
     */
    public LawyerVo getLawyer1(Long lawyerId) {
        // 初始化 LawyerVo 对象和专长关联信息列表
        LawyerVo lawyerVo = new LawyerVo();
        List<LawyerSpecialtyRelationVo> lawyerSpecialtyRelationVoList = new ArrayList<>();
        List<String> specialtyNamesList = new ArrayList<>();

        // 查询律师基本信息
        Lawyer lawyer = lawyerDao.selectByPrimaryKey(lawyerId);

        // 查询律师的专长关联信息
        List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyer.getId());

        // 处理专长关联信息
        if (lawyerSpecialtyRelationList != null && !lawyerSpecialtyRelationList.isEmpty()) {
            for (LawyerSpecialtyRelation relation : lawyerSpecialtyRelationList) {
                // 创建专长关联信息视图对象和专长信息视图对象
                LawyerSpecialtyRelationVo relationVo = new LawyerSpecialtyRelationVo();
                LawyerSpecialtyVo specialtyVo = new LawyerSpecialtyVo();

                // 查询专长详细信息
                LawyerSpecialty specialty = lawyerSpecialtyDao.selectByPrimaryKey(relation.getSpecialtyId());

                // 复制信息到视图对象
                CommonUtil.copyProperties(specialty, specialtyVo);
                CommonUtil.copyProperties(relation, relationVo);

                // 关联专长信息到专长关联信息视图对象
                relationVo.setLawyerSpecialtyVo(specialtyVo);
                // 添加到专长关联信息视图对象列表
                lawyerSpecialtyRelationVoList.add(relationVo);

                // 提取专长名称添加到specialtyNamesList列表
                if (specialty.getSpecialtyName() != null && !specialty.getSpecialtyName().isEmpty()) {
                    specialtyNamesList.add(specialty.getSpecialtyName());
                }
            }
        }

        // 复制律师基本信息到 LawyerVo 对象
        CommonUtil.copyProperties(lawyer, lawyerVo);
        // 设置专长关联信息视图对象列表到 LawyerVo 对象
        lawyerVo.setLawyerSpecialtyRelationVolist(lawyerSpecialtyRelationVoList);
        // 设置专长名称逗号分隔字符串
        lawyerVo.setSpecialtyNames(String.join(",", specialtyNamesList));

        // 查询关联的用户信息和头像
        fetchUserInfoAndAvatar(lawyerId, lawyerVo);

        return lawyerVo;
    }

    /**
     * 获取所有律师的 ID 列表
     *
     * @return 所有律师的 ID 列表
     */
    public List<Long> getAllLawyerIds() {
        // 创建查询条件，这里使用空条件查询所有律师
        Lawyer queryCondition = new Lawyer();
        // 设置有效标志，只查询有效的律师
        queryCondition.setIsValidFlag("1");

        // 使用 PageParam 进行分页查询，但设置一个足够大的页码以获取所有记录
        ins.framework.mybatis.PageParam pageParam = new ins.framework.mybatis.PageParam();
        //pageParam.setPageSize(1000); // 设置一个较大的页大小

        // 查询所有符合条件的律师
        ins.framework.mybatis.Page<Lawyer> lawyerPage = lawyerDao.selectPage(pageParam, queryCondition);

        // 提取律师 ID 列表
        List<Long> lawyerIds = new ArrayList<>();
        for (Lawyer lawyer : lawyerPage) {
            lawyerIds.add(lawyer.getId());
        }

        return lawyerIds;
    }

    /**
     * 获取所有律师的信息列表
     *
     * @return 包含所有律师信息的 List<LawyerVo> 对象列表
     */
    public List<LawyerVo> getAllLawyers() {
        // 创建查询条件，这里使用空条件查询所有律师
        Lawyer queryCondition = new Lawyer();
        // 设置有效标志，只查询有效的律师
        queryCondition.setIsValidFlag("1");

        // 使用 PageParam 进行分页查询，但设置一个足够大的页码以获取所有记录
        PageParam pageParam = new PageParam();

        // 查询所有符合条件的律师
        Page<Lawyer> lawyerPage = lawyerDao.selectPage(pageParam, queryCondition);

        // 转换结果为VO对象列表并填充关联数据
        List<LawyerVo> lawyerVoList = new ArrayList<>();
        for (Lawyer lawyer : lawyerPage) {
            // 使用convertToVo方法确保专长信息被正确处理
            LawyerVo lawyerVo = convertToVo(lawyer);
            lawyerVoList.add(lawyerVo);
        }

        return lawyerVoList;
    }

    /**
     * @description: 根据条件查询律师
     * @author: dongzhibo
     * @date: 2025/5/1 15:55
     * @param:
     * @return:
     **/
    public PageResult<LawyerVo> searchLawyers(LawyerSearchVo params) {
        // 计算分页参数
        int offset = (params.getPage() - 1) * params.getPageSize();

        // 构建查询条件
        LawyerQueryWrapper queryWrapper = new LawyerQueryWrapper();

        // 关键词搜索(律师名称或简介)
        if (StringUtils.hasText(params.getKeyword())) {
            queryWrapper.keywordLike(params.getKeyword());
        }

        // 专业领域筛选
        if (StringUtils.hasText(params.getSpecialty())) {
            queryWrapper.specialtyEquals(params.getSpecialty());
        }

        // 排除特定专业领域
        if (params.getExcludeSpecialties() != null && !params.getExcludeSpecialties().isEmpty()) {
            queryWrapper.excludeSpecialties(params.getExcludeSpecialties());
        }

        // 地区筛选
        if (StringUtils.hasText(params.getProvinceCode())) {
            queryWrapper.provinceCodeEquals(params.getProvinceCode());
        }

        if (StringUtils.hasText(params.getCityCode())) {
            queryWrapper.cityCodeEquals(params.getCityCode());
        }

        if (StringUtils.hasText(params.getDistrictCode())) {
            queryWrapper.districtCodeEquals(params.getDistrictCode());
        }

        // 是否推荐筛选
        if (params.getRecommend() != null) {
            queryWrapper.recommendEquals(params.getRecommend());
        }

        // 从业年限范围筛选
        if (params.getExperienceMin() != null || params.getExperienceMax() != null) {
            queryWrapper.experienceRange(params.getExperienceMin(), params.getExperienceMax());
        }

        // 设置默认排序方式，按评分降序
        queryWrapper.orderBy("rating DESC, id DESC");

        // 查询总记录数
        long total = lawyerDao.countByCondition(queryWrapper);

        // 如果总记录数为0，直接返回空结果
        if (total == 0) {
            return PageResult.of(Collections.emptyList(), 0, params.getPage(), params.getPageSize());
        }

        // 查询当前分页数据
        List<LawyerInfoDTO> lawyers = lawyerDao.selectByCondition(queryWrapper, offset, params.getPageSize());

        // 转换为VO并填充关联数据
        List<LawyerVo> lawyerVos = lawyers.stream()
                .map(this::convertToVo)
                .collect(Collectors.toList());

        // 返回分页结果
        return PageResult.of(lawyerVos, total, params.getPage(), params.getPageSize());
    }

    /**
     * 将Lawyer实体转换为LawyerVo
     */
    private LawyerVo convertToVo(Lawyer lawyer) {
        if (lawyer == null) {
            return null;
        }

        LawyerVo vo = new LawyerVo();
        BeanUtils.copyProperties(lawyer, vo);

        // 查询并填充律师专长信息
        List<LawyerSpecialtyRelation> specialtyRelations = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyer.getId());

        if (specialtyRelations != null && !specialtyRelations.isEmpty()) {
            List<LawyerSpecialtyRelationVo> specialtyVos = new ArrayList<>();
            List<String> specialtyNamesList = new ArrayList<>();

            for (LawyerSpecialtyRelation relation : specialtyRelations) {
                // 创建专长关联信息视图对象
                LawyerSpecialtyRelationVo relationVo = new LawyerSpecialtyRelationVo();
                CommonUtil.copyProperties(relation, relationVo);

                // 查询专长详细信息
                LawyerSpecialty specialty = lawyerSpecialtyDao.selectByPrimaryKey(relation.getSpecialtyId());
                if (specialty != null) {
                    LawyerSpecialtyVo specialtyVo = new LawyerSpecialtyVo();
                    CommonUtil.copyProperties(specialty, specialtyVo);
                    relationVo.setLawyerSpecialtyVo(specialtyVo);

                    // 直接提取专长名称添加到specialtyNamesList列表
                    if (specialty.getSpecialtyName() != null && !specialty.getSpecialtyName().isEmpty()) {
                        specialtyNamesList.add(specialty.getSpecialtyName());
                    }
                }

                specialtyVos.add(relationVo);
            }

            vo.setLawyerSpecialtyRelationVolist(specialtyVos);

            // 直接设置专长名称逗号分隔字符串
            vo.setSpecialtyNames(String.join(",", specialtyNamesList));
        }

        // 查询关联的用户信息、电话号码和头像
        fetchUserInfoAndAvatar(lawyer.getId(), vo);

        // 翻译地址信息
        translateAddress(vo);

        return vo;
    }

    /**
     * 将LawyerInfoDTO转换为LawyerVo
     */
    private LawyerVo convertToVo(LawyerInfoDTO lawyerDTO) {
        if (lawyerDTO == null) {
            return null;
        }

        LawyerVo vo = new LawyerVo();
        BeanUtils.copyProperties(lawyerDTO, vo);

        // 查询并填充律师专长信息
        List<LawyerSpecialtyRelation> specialtyRelations = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyerDTO.getId());

        if (specialtyRelations != null && !specialtyRelations.isEmpty()) {
            List<LawyerSpecialtyRelationVo> specialtyVos = new ArrayList<>();
            List<String> specialtyNamesList = new ArrayList<>();

            for (LawyerSpecialtyRelation relation : specialtyRelations) {
                // 创建专长关联信息视图对象
                LawyerSpecialtyRelationVo relationVo = new LawyerSpecialtyRelationVo();
                CommonUtil.copyProperties(relation, relationVo);

                // 查询专长详细信息
                LawyerSpecialty specialty = lawyerSpecialtyDao.selectByPrimaryKey(relation.getSpecialtyId());
                if (specialty != null) {
                    LawyerSpecialtyVo specialtyVo = new LawyerSpecialtyVo();
                    CommonUtil.copyProperties(specialty, specialtyVo);
                    relationVo.setLawyerSpecialtyVo(specialtyVo);

                    // 直接提取专长名称添加到specialtyNamesList列表
                    if (specialty.getSpecialtyName() != null && !specialty.getSpecialtyName().isEmpty()) {
                        specialtyNamesList.add(specialty.getSpecialtyName());
                    }
                }

                specialtyVos.add(relationVo);
            }

            vo.setLawyerSpecialtyRelationVolist(specialtyVos);

            // 直接设置专长名称逗号分隔字符串
            vo.setSpecialtyNames(String.join(",", specialtyNamesList));
        }

        // 处理头像数据 - 如果已经从数据库获取到头像数据，则直接设置
        if (lawyerDTO.getUserId() != null) {
            vo.setUserId(lawyerDTO.getUserId());

            // 如果已经通过JOIN查询获取了头像数据
            if (lawyerDTO.getAvatarData() != null) {
                // 转换为Base64字符串
                String base64Image = Base64.encodeBase64String(lawyerDTO.getAvatarData());
                vo.setImageData(base64Image);

                // 设置图片类型
                if (lawyerDTO.getAvatarExtension() != null) {
                    String imageType = ImageUtil.getMimeTypeFromExtension(lawyerDTO.getAvatarExtension());
                    vo.setImageType(imageType);
                }
            } else {
                // 如果没有通过JOIN获取到头像数据，则查询头像 (备用方法)
                ImageStorage avatar = imageStorageDao.selectLatestAvatarByUserId(lawyerDTO.getUserId());
                if (avatar != null && avatar.getImageData() != null) {
                    // 转换为Base64字符串
                    String base64Image = Base64.encodeBase64String(avatar.getImageData());
                    vo.setImageData(base64Image);

                    // 设置图片类型
                    String imageType = ImageUtil.getMimeTypeFromExtension(avatar.getFileExtension());
                    vo.setImageType(imageType);
                }
            }
        } else {
            // 查询关联的用户信息和头像(备用方法)
            fetchUserInfoAndAvatar(lawyerDTO.getId(), vo);
        }

        // 翻译地址信息
        translateAddress(vo);

        return vo;
    }

}
