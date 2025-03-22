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
    LawyerDao lawyersDao;
    @Autowired
    LawyerSpecialtyRelationDao lawyerSpecialtyRelationsDao;
    @Autowired
    LawyerSpecialtieDao lawyerSpecialtiesDao;

    public void updateLawyer(LawyerVo lawyersVo) {
        Lawyer lawyer = new Lawyer();
        CommonUtil.copyProperties(lawyersVo, lawyer);
        lawyersDao.updateSelectiveByPrimaryKey(lawyer);
        if (lawyersVo.getLawyerSpecialtyRelationsVolist() != null && lawyersVo.getLawyerSpecialtyRelationsVolist().size() > 0) {
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationVoList = new ArrayList<>();
            CommonUtil.copyProperties(lawyersVo.getLawyerSpecialtyRelationsVolist(), lawyerSpecialtyRelationVoList);
            List<LawyerSpecialtyRelation> insertList = new ArrayList<>();
            List<LawyerSpecialtyRelation> updateList = new ArrayList<>();
            List<Long> deleteList = new ArrayList<>();
            HashMap<Long, LawyerSpecialtyRelation> poIdMap = new HashMap<>();
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationsDao.selectBatchByLawyerId(lawyer.getId());
            for (LawyerSpecialtyRelation lsrPo : lawyerSpecialtyRelationList) {
                poIdMap.put(lsrPo.getId(), lsrPo);
            }
            for (LawyerSpecialtyRelation lsrVo : lawyerSpecialtyRelationVoList) {
                if (lsrVo.getId() == null) {
                    insertList.add(lsrVo);
                } else if (poIdMap.containsKey(lsrVo.getId())) {
                    updateList.add(lsrVo);
                } else {
                    deleteList.add(lsrVo.getId());
                }
            }
            if (insertList != null && insertList.size() > 0) {
                lawyerSpecialtyRelationsDao.insertList(insertList);
            }
            if (updateList != null && updateList.size() > 0) {
                lawyerSpecialtyRelationsDao.updateList(updateList);
            }
            if (deleteList != null && deleteList.size() > 0) {
                lawyerSpecialtyRelationsDao.deleteBatchByPrimaryKeys(deleteList);
            }
        }
    }

    public LawyerVo getLawyer(Long lawyerId) {
        LawyerVo lawyerVo = new LawyerVo();
        List<LawyerSpecialtyRelationVo> lawyerSpecialtyRelationVoList = new ArrayList<>();
        Lawyer lawyer = lawyersDao.selectByPrimaryKey(lawyerId);

        List<LawyerSpecialtyRelation> lawyerSpecialtyRelationList = lawyerSpecialtyRelationsDao.selectBatchByLawyerId(lawyer.getId());
        if (lawyerSpecialtyRelationList != null && lawyerSpecialtyRelationList.size() > 0) {
            for (LawyerSpecialtyRelation lawyerSpecialtyRelation : lawyerSpecialtyRelationList) {
                LawyerSpecialtyRelationVo lawyerSpecialtyRelationVo = new LawyerSpecialtyRelationVo();
                LawyerSpecialtieVo lawyerSpecialtieVo = new LawyerSpecialtieVo();
                LawyerSpecialtie lawyerSpecialtie = lawyerSpecialtiesDao.selectByPrimaryKey(lawyerSpecialtyRelation.getSpecialtyId());
                CommonUtil.copyProperties(lawyerSpecialtie, lawyerSpecialtieVo);
                CommonUtil.copyProperties(lawyerSpecialtyRelation, lawyerSpecialtyRelationVo);
                lawyerSpecialtyRelationVo.setLawyerSpecialtiesVo(lawyerSpecialtieVo);
                lawyerSpecialtyRelationVoList.add(lawyerSpecialtyRelationVo);
            }
        }
        CommonUtil.copyProperties(lawyer, lawyerVo);
        lawyerVo.setLawyerSpecialtyRelationsVolist(lawyerSpecialtyRelationVoList);
        return lawyerVo;
    }


}
