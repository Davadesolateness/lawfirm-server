package com.lawfirm.lawfirmserver.lawyer.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtieDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyRelationDao;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtie;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtyRelation;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerSpecialtieVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerSpecialtyRelationVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    LawyerSpecialtieDao lawyerSpecialtieDao;

    /**
     * 更新律师信息及其关联的专长信息
     *
     * @param lawyerVo 包含律师信息和专长关联信息的视图对象
     */
    public void updateLawyer(LawyerVo lawyerVo) {
        // 创建 Lawyer 对象并复制视图对象的律师信息
        Lawyer lawyer = new Lawyer();
        CommonUtil.copyProperties(lawyerVo, lawyer);
        // 根据主键选择性更新律师信息
        lawyerDao.updateSelectiveByPrimaryKey(lawyer);

        // 检查专长关联信息列表是否存在且不为空
        if (lawyerVo.getLawyerSpecialtyRelationVolist() != null && !lawyerVo.getLawyerSpecialtyRelationVolist().isEmpty()) {
            // 复制视图对象的专长关联信息
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationVoList = new ArrayList<>();
            CommonUtil.copyProperties(lawyerVo.getLawyerSpecialtyRelationVolist(), lawyerSpecialtyRelationVoList);

            // 分别存储需插入、更新、删除的专长关联信息
            List<LawyerSpecialtyRelation> insertList = new ArrayList<>();
            List<LawyerSpecialtyRelation> updateList = new ArrayList<>();
            List<Long> deleteList = new ArrayList<>();

            // 存储从数据库查询的专长关联信息，键为 ID
            HashMap<Long, LawyerSpecialtyRelation> poIdMap = new HashMap<>();
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationDao.selectBatchByLawyerId(lawyer.getId());
            for (LawyerSpecialtyRelation lsrPo : lawyerSpecialtyRelationList) {
                poIdMap.put(lsrPo.getId(), lsrPo);
            }

            // 遍历复制的专长关联信息，分类添加到对应列表
            for (LawyerSpecialtyRelation lsrVo : lawyerSpecialtyRelationVoList) {
                if (lsrVo.getId() == null) {
                    insertList.add(lsrVo);
                } else if (poIdMap.containsKey(lsrVo.getId())) {
                    updateList.add(lsrVo);
                } else {
                    deleteList.add(lsrVo.getId());
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
                LawyerSpecialtieVo specialtyVo = new LawyerSpecialtieVo();

                // 查询专长详细信息
                LawyerSpecialtie specialty = lawyerSpecialtieDao.selectByPrimaryKey(relation.getSpecialtyId());

                // 复制信息到视图对象
                CommonUtil.copyProperties(specialty, specialtyVo);
                CommonUtil.copyProperties(relation, relationVo);

                // 关联专长信息到专长关联信息视图对象
                relationVo.setLawyerSpecialtieVo(specialtyVo);
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


}
