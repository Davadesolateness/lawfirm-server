package com.lawfirm.lawfirmserver.lawyer.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerDao;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerSpecialtyRelationDao;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.lawyer.po.LawyerSpecialtyRelation;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LawyerService {

    @Autowired
    LawyerDao lawyersDao;
    @Autowired
    LawyerSpecialtyRelationDao lawyerSpecialtyRelationsDao;

    public void updateLawyer(LawyerVo lawyersVo) {
        Lawyer lawyers = new Lawyer();
        CommonUtil.copyProperties(lawyersVo, lawyers);
        lawyersDao.updateSelectiveByPrimaryKey(lawyers);
        if (lawyersVo.getLawyerSpecialtyRelationsVolist() != null && lawyersVo.getLawyerSpecialtyRelationsVolist().size() > 0) {
            List<LawyerSpecialtyRelation> lawyerSpecialtyRelationsList = lawyerSpecialtyRelationsDao.selectBatchByLawyerId(lawyers.getId());
        }
    }
}
