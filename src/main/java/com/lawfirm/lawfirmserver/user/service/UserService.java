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
        // 创建 User 对象，用于存储通用用户信息
        User user = new User();
        // 创建 CorporateClients 对象，用于存储法人客户的额外信息
        CorporateClients corporateClients = new CorporateClients();
        // 创建 IndividualClients 对象，用于存储个人客户的额外信息
        IndividualClients individualClients = new IndividualClients();
        // 创建 Lawyers 对象，用于存储律师的额外信息
        Lawyers lawyers = new Lawyers();
        // 创建 Administrators 对象，用于存储管理员的额外信息
        Administrators administrators = new Administrators();

        // 使用 CommonUtil 工具类将 userPageVo 中的用户通用信息复制到 user 对象中
        CommonUtil.copyProperties(userPageVo.getUserVo(), user);
        // 使用 CommonUtil 工具类将 userPageVo 中的法人客户信息复制到 corporateClients 对象中
        CommonUtil.copyProperties(userPageVo.getCorporateClientsVo(), corporateClients);
        // 使用 CommonUtil 工具类将 userPageVo 中的个人客户信息复制到 individualClients 对象中
        CommonUtil.copyProperties(userPageVo.getIndividualClientsVo(), individualClients);
        // 使用 CommonUtil 工具类将 userPageVo 中的律师信息复制到 lawyers 对象中
        CommonUtil.copyProperties(userPageVo.getLawyersVo(), lawyers);
        // 使用 CommonUtil 工具类将 userPageVo 中的管理员信息复制到 administrators 对象中
        CommonUtil.copyProperties(userPageVo.getAdministratorsVo(), administrators);

        // 判断用户 ID 是否为空，如果为空则执行插入操作
        if (userPageVo.getUserVo().getId() == null) {
            // 调用 userDao 的 insertSelectiveAndBackId 方法将 user 对象插入数据库，并返回插入后的用户 ID
            userDao.insertSelectiveAndBackId(user);
            // 对用户密码进行加密处理，并更新到 user 对象中
            user.setPassword(CommonUtil.encryptPassword(userPageVo.getUserVo().getPassword()));
            // 设置用户密码更新时间为当前时间
            user.setPasswordUpdateTime(new Date());

            // 根据用户类型进行不同的处理
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                // 如果是法人客户类型，调用 corporateClientsDao 的 insertSelectiveAndBackId 方法将法人客户信息插入数据库，并返回插入后的法人客户 ID
                corporateClientsDao.insertSelectiveAndBackId(corporateClients);
                // 将法人客户的 ID 设置到 user 对象的 relatedEntityId 字段中，建立关联
                user.setRelatedEntityId(corporateClients.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                // 如果是个人客户类型，调用 individualClientsDao 的 insertSelectiveAndBackId 方法将个人客户信息插入数据库，并返回插入后的个人客户 ID
                individualClientsDao.insertSelectiveAndBackId(individualClients);
                // 将个人客户的 ID 设置到 user 对象的 relatedEntityId 字段中，建立关联
                user.setRelatedEntityId(individualClients.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                // 如果是律师类型，调用 lawyersDao 的 insertSelectiveAndBackId 方法将律师信息插入数据库，并返回插入后的律师 ID
                lawyersDao.insertSelectiveAndBackId(lawyers);
                // 将律师的 ID 设置到 user 对象的 relatedEntityId 字段中，建立关联
                user.setRelatedEntityId(lawyers.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                // 如果是管理员类型，调用 administratorsDao 的 insertSelectiveAndBackId 方法将管理员信息插入数据库，并返回插入后的管理员 ID
                administratorsDao.insertSelectiveAndBackId(administrators);
                // 将管理员的 ID 设置到 user 对象的 relatedEntityId 字段中，建立关联
                user.setRelatedEntityId(administrators.getId());
            }
            // 调用 userDao 的 updateSelectiveByPrimaryKey 方法更新 user 对象的信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);
        } else {
            // 如果用户 ID 不为空，调用 userDao 的 updateSelectiveByPrimaryKey 方法更新 user 对象的信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);

            // 根据用户类型进行不同的处理
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                // 如果是法人客户类型，调用 corporateClientsDao 的 updateSelectiveByPrimaryKey 方法更新法人客户信息到数据库
                corporateClientsDao.updateSelectiveByPrimaryKey(corporateClients);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                // 如果是个人客户类型，调用 individualClientsDao 的 updateSelectiveByPrimaryKey 方法更新个人客户信息到数据库
                individualClientsDao.updateSelectiveByPrimaryKey(individualClients);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                // 如果是律师类型，调用 lawyersDao 的 updateSelectiveByPrimaryKey 方法更新律师信息到数据库
                lawyersDao.updateSelectiveByPrimaryKey(lawyers);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                // 如果是管理员类型，调用 administratorsDao 的 updateSelectiveByPrimaryKey 方法更新管理员信息到数据库
                administratorsDao.updateSelectiveByPrimaryKey(administrators);
            }
        }

        // 使用 CommonUtil 工具类将更新后的 user 对象信息复制回 userPageVo 中的用户通用信息部分
        CommonUtil.copyProperties(user, userPageVo.getUserVo());
        // 使用 CommonUtil 工具类将更新后的 corporateClients 对象信息复制回 userPageVo 中的法人客户信息部分
        CommonUtil.copyProperties(corporateClients, userPageVo.getCorporateClientsVo());
        // 使用 CommonUtil 工具类将更新后的 individualClients 对象信息复制回 userPageVo 中的个人客户信息部分
        CommonUtil.copyProperties(individualClients, userPageVo.getIndividualClientsVo());
        // 使用 CommonUtil 工具类将更新后的 lawyers 对象信息复制回 userPageVo 中的律师信息部分
        CommonUtil.copyProperties(lawyers, userPageVo.getLawyersVo());
        // 使用 CommonUtil 工具类将更新后的 administrators 对象信息复制回 userPageVo 中的管理员信息部分
        CommonUtil.copyProperties(administrators, userPageVo.getAdministratorsVo());
        // 设置 userPageVo 的操作结果为 true，表示操作成功
        userPageVo.setResult(true);
    }
}
