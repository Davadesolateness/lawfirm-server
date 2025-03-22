package com.lawfirm.lawfirmserver.user.vo;

import com.lawfirm.lawfirmserver.lawyer.vo.LawyersVo;
import lombok.Data;

@Data
public class UserPageVo {

    /*用户*/
    private UserVo userVo;
    /*律师*/
    private LawyersVo lawyersVo;
    /*个人客户*/
    private IndividualClientsVo individualClientsVo;
    /*法人客户*/
    private CorporateClientsVo corporateClientsVo;
    /*管理员*/
    private AdministratorsVo administratorsVo;

    private boolean result;
}
