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
     * 验证用户登录信息的方法。
     * 该方法接收用户名和明文密码作为参数，通过用户名从数据库中查询对应的用户信息，
     * 并将输入的明文密码与数据库中存储的加密密码进行比对，以此来验证用户登录信息的正确性。
     *
     * @param username      用户登录时输入的用户名，用于从数据库中查找对应的用户记录。
     * @param plainPassword 用户登录时输入的明文密码，将与数据库中存储的加密密码进行比对。
     * @return 如果用户名对应的用户存在，且输入的明文密码与数据库中存储的加密密码匹配，则返回 true；
     * 否则返回 false，表示登录验证失败。
     */
    public boolean validateLogin(String username, String plainPassword) {
        // 调用 userDao 的 selectUserByUserName 方法，根据传入的用户名从数据库中查询对应的用户信息
        User user = userDao.selectUserByUserName(username);
        // 初始化验证结果为 false，表示默认登录验证失败
        Boolean result = false;
        // 判断根据用户名查询到的用户是否存在
        if (user != null) {
            // 如果用户存在，调用 CommonUtil 的 checkPassword 方法，将输入的明文密码与数据库中存储的加密密码进行比对
            result = CommonUtil.checkPassword(plainPassword, user.getPassword());
        }
        // 返回最终的登录验证结果
        return result;
    }

    /**
     * 保存或更新用户信息的方法。
     * 该方法接收一个 UserPageVo 对象，根据对象中的用户信息进行保存或更新操作。
     * 如果用户 ID 为空，则执行插入操作；如果用户 ID 不为空，则执行更新操作。
     * 同时，根据用户类型，对相应的关联实体信息也进行保存或更新操作。
     * 最后将更新后的信息复制回 UserPageVo 对象，并设置操作结果为 true。
     *
     * @param userPageVo 包含用户信息和关联实体信息的 UserPageVo 对象
     */
    public void saveOrUpdateUser(UserPageVo userPageVo) {
        // 模块一：对象初始化
        // 创建用于存储不同类型信息的对象
        User user = new User();
        CorporateClients corporateClients = new CorporateClients();
        IndividualClients individualClients = new IndividualClients();
        Lawyers lawyers = new Lawyers();
        Administrators administrators = new Administrators();

        // 模块二：信息复制
        // 将 userPageVo 中的各部分信息复制到对应的对象中
        CommonUtil.copyProperties(userPageVo.getUserVo(), user);
        CommonUtil.copyProperties(userPageVo.getCorporateClientsVo(), corporateClients);
        CommonUtil.copyProperties(userPageVo.getIndividualClientsVo(), individualClients);
        CommonUtil.copyProperties(userPageVo.getLawyersVo(), lawyers);
        CommonUtil.copyProperties(userPageVo.getAdministratorsVo(), administrators);

        // 模块三：插入或更新操作
        if (userPageVo.getUserVo().getId() == null) {
            // 插入操作
            // 插入用户信息并获取插入后的 ID
            userDao.insertSelectiveAndBackId(user);
            // 加密用户密码并更新密码更新时间
            user.setPassword(CommonUtil.encryptPassword(userPageVo.getUserVo().getPassword()));
            user.setPasswordUpdateTime(new Date());

            // 根据用户类型插入关联实体信息并建立关联
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
            // 更新用户信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);
        } else {
            // 更新操作
            // 更新用户信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);

            // 根据用户类型更新关联实体信息
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

        // 模块四：结果回写和状态设置
        // 将更新后的信息复制回 userPageVo 中
        CommonUtil.copyProperties(user, userPageVo.getUserVo());
        CommonUtil.copyProperties(corporateClients, userPageVo.getCorporateClientsVo());
        CommonUtil.copyProperties(individualClients, userPageVo.getIndividualClientsVo());
        CommonUtil.copyProperties(lawyers, userPageVo.getLawyersVo());
        CommonUtil.copyProperties(administrators, userPageVo.getAdministratorsVo());
        // 设置操作结果为成功
        userPageVo.setResult(true);
    }
}
