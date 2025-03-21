package com.lawfirm.lawfirmserver.user.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.user.consts.UserContant;
import com.lawfirm.lawfirmserver.user.dao.*;
import com.lawfirm.lawfirmserver.user.po.*;
import com.lawfirm.lawfirmserver.user.vo.UserPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private LawyersDao lawyersDao;
    @Autowired
    private CorporateClientsDao corporateClientsDao;
    @Autowired
    private IndividualClientsDao individualClientsDao;
    @Autowired
    private AdministratorsDao administratorsDao;

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

    public void saveOrUpdateUser(UserPageVo userPageVo) {
        User user = new User();
        CorporateClients corporateClients = new CorporateClients();
        IndividualClients individualClients = new IndividualClients();
        Lawyers lawyers = new Lawyers();
        Administrators administrators = new Administrators();

        CommonUtil.copyProperties(userPageVo.getUserVo(), user);
        CommonUtil.copyProperties(userPageVo.getCorporateClientsVo(), corporateClients);
        CommonUtil.copyProperties(userPageVo.getIndividualClientsVo(), individualClients);
        CommonUtil.copyProperties(userPageVo.getLawyersVo(), lawyers);
        CommonUtil.copyProperties(userPageVo.getAdministratorsVo(), administrators);

        if (userPageVo.getUserVo().getId() == null) {
            userDao.insertSelectiveAndBackId(user);
            user.setPassword(CommonUtil.encryptPassword(userPageVo.getUserVo().getPassword()));
            user.setPasswordUpdateTime(new Date());
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                corporateClientsDao.insertSelectiveAndBackId(corporateClients);
                user.setRelatedEntityId(corporateClients.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                individualClientsDao.insertSelectiveAndBackId(individualClients);
                user.setRelatedEntityId(individualClients.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                lawyersDao.insertSelectiveAndBackId(lawyers);
                user.setRelatedEntityId(lawyers.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                administratorsDao.insertSelectiveAndBackId(administrators);
                user.setRelatedEntityId(administrators.getId());
            }
            userDao.updateSelectiveByPrimaryKey(user);
        } else {
            userDao.updateSelectiveByPrimaryKey(user);
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                corporateClientsDao.updateSelectiveByPrimaryKey(corporateClients);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                individualClientsDao.updateSelectiveByPrimaryKey(individualClients);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                lawyersDao.updateSelectiveByPrimaryKey(lawyers);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                administratorsDao.updateSelectiveByPrimaryKey(administrators);
            }
        }

        CommonUtil.copyProperties(user, userPageVo.getUserVo());
        CommonUtil.copyProperties(corporateClients, userPageVo.getCorporateClientsVo());
        CommonUtil.copyProperties(individualClients, userPageVo.getIndividualClientsVo());
        CommonUtil.copyProperties(lawyers, userPageVo.getLawyersVo());
        CommonUtil.copyProperties(administrators, userPageVo.getAdministratorsVo());
        userPageVo.setResult(true);
    }
}
