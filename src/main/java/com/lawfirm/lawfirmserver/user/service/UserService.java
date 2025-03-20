package com.lawfirm.lawfirmserver.user.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.user.dao.UserDao;
import com.lawfirm.lawfirmserver.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 校验用户登录信息
     *
     * @param username      用户名
     * @param plainPassword 明文密码
     * @return 如果用户名和密码匹配返回 true，否则返回 false
     */
    public boolean validateLogin(String username, String plainPassword) {
        User user = userDao.selectUserByUserName(username);
        Boolean result = false;
        if (user != null) {
            result = CommonUtil.checkPassword(plainPassword, user.getPassword());
        }
        return result;
    }
}
