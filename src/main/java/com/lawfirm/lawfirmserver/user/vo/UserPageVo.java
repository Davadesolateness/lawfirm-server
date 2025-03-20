package com.lawfirm.lawfirmserver.user.vo;

import lombok.Data;

@Data
public class UserPageVo {
    private String username;
    /**
     * 对应字段：password,备注：加密后的密码
     */
    private String password;

    private boolean result;
}
