package com.lawfirm.lawfirmserver.user.service;

import com.lawfirm.lawfirmserver.user.dao.UsersDao;
import com.lawfirm.lawfirmserver.user.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsersDao userDao;

    public boolean checkUser(String username, String password) {
        Users users = userDao.selectUsersByUserName(username);
        System.out.println(users.toString());
        return false;
    }
}
