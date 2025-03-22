package com.lawfirm.lawfirmserver.user.vo;

import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import lombok.Data;

@Data
public class UserPageVo {

    /*用户*/
    private UserVo userVo;
    /*律师*/
    private LawyerVo lawyerVo;
    /*个人客户*/
    private IndividualClientVo individualClientVo;
    /*法人客户*/
    private CorporateClientVo corporateClientVo;
    /*管理员*/
    private AdministratorVo administratorVo;

    private boolean result;
}
