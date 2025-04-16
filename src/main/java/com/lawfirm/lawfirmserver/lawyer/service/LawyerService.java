package com.lawfirm.lawfirmserver.lawyer.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyRelationDao;
import com.lawfirm.lawfirmserver.lawyer.dto.LawyerQueryDTO;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialty;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtyRelation;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerSpecialtyRelationVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerSpecialtyVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import ins.framework.mybatis.Page;
import ins.framework.mybatis.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LawyerService {

    @Autowired
    LawyerDao lawyerDao;
    @Autowired
    LawyerSpecialtyRelationDao lawyerSpecialtyRelationDao;
    @Autowired
    LawyerSpecialtyDao lawyerSpecialtyDao;

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
     * 根据律师 ID 获取律师信息及其关联专长信息
     *
     * @param lawyerId 律师 ID
     * @return 封装律师信息和专长信息的 LawyerVo 对象
     */
    public LawyerVo getLawyer(Long lawyerId) {
        // 初始化 LawyerVo 对象和专长关联信息列表
        LawyerVo lawyerVo = new LawyerVo();
        List<LawyerSpecialtyRelationVo> lawyerSpecialtyRelationVoList = new ArrayList<>();

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
            }
        }

        // 复制律师基本信息到 LawyerVo 对象
        CommonUtil.copyProperties(lawyer, lawyerVo);
        // 设置专长关联信息视图对象列表到 LawyerVo 对象
        lawyerVo.setLawyerSpecialtyRelationVolist(lawyerSpecialtyRelationVoList);

        return lawyerVo;
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
}
