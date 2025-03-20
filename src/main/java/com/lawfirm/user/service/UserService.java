package com.lawfirm.user.service;

import com.lawfirm.user.dao.UsersDao;
import com.lawfirm.user.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsersDao userDao;

    public boolean checkUser(String userName, String passWord) {
        Users users = userDao.selectByPrimaryKey(Long.valueOf(11111));
        System.out.println(users.toString());
        return false;
    }
}
