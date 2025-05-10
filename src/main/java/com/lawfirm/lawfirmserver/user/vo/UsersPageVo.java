package com.lawfirm.lawfirmserver.user.vo;

import com.lawfirm.lawfirmserver.admin.vo.AdministratorVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import lombok.Data;

@Data
public class UsersPageVo {

    /*用户*/
    private UsersVo usersVo;
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
