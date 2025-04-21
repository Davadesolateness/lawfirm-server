package com.lawfirm.lawfirmserver.user.service;

import com.lawfirm.lawfirmserver.common.util.CommonUtil;
import com.lawfirm.lawfirmserver.lawyer.dao.LawyerDao;
import com.lawfirm.lawfirmserver.lawyer.po.Lawyer;
import com.lawfirm.lawfirmserver.user.consts.UserContant;
import com.lawfirm.lawfirmserver.user.dao.*;
import com.lawfirm.lawfirmserver.user.po.*;
import com.lawfirm.lawfirmserver.user.vo.CorporateDetailsVo;
import com.lawfirm.lawfirmserver.user.vo.IndividualDetailsVo;
import com.lawfirm.lawfirmserver.user.vo.UserPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private LawyerDao lawyerDao;
    @Autowired
    private CorporateClientDao corporateClientDao;
    @Autowired
    private IndividualClientDao individualClientDao;
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private CustomerServiceInfoDao customerServiceInfoDao;

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
     * 修改用户密码的方法
     *
     * @param username    用户名称
     * @param oldPassword 用户输入的旧密码
     * @param newPassword 用户输入的新密码
     * @return 如果密码修改成功返回 true，否则返回 false
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 根据用户名查询用户信息
        User user = userDao.selectUserByUserName(username);
        if (user == null) {
            return false;
        }
        // 验证旧密码是否正确
        if (!CommonUtil.checkPassword(oldPassword, user.getPassword())) {
            return false;
        }
        // 对新密码进行加密处理
        String encryptedNewPassword = CommonUtil.encryptPassword(newPassword);
        user.setPassword(encryptedNewPassword);
        // 更新密码修改时间为当前时间
        user.setPasswordUpdateTime(new Date());
        // 将更新后的用户信息保存到数据库
        userDao.updateSelectiveByPrimaryKey(user);
        // 密码修改成功，返回 true
        return true;
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
        // 创建用于存储不同类型信息的对象
        User user = new User();
        CorporateClient corporateClient = new CorporateClient();
        IndividualClient individualClient = new IndividualClient();
        Lawyer lawyer = new Lawyer();
        Administrator administrator = new Administrator();

        // 将 userPageVo 中的各部分信息复制到对应的对象中
        CommonUtil.copyProperties(userPageVo.getUserVo(), user);
        CommonUtil.copyProperties(userPageVo.getCorporateClientVo(), corporateClient);
        CommonUtil.copyProperties(userPageVo.getIndividualClientVo(), individualClient);
        CommonUtil.copyProperties(userPageVo.getLawyerVo(), lawyer);
        CommonUtil.copyProperties(userPageVo.getAdministratorVo(), administrator);

        if (userPageVo.getUserVo().getId() == null) {
            // 插入用户信息并获取插入后的 ID
            userDao.insertSelectiveAndBackId(user);
            // 加密用户密码并更新密码更新时间
            user.setPassword(CommonUtil.encryptPassword(userPageVo.getUserVo().getPassword()));
            user.setPasswordUpdateTime(new Date());

            // 根据用户类型插入关联实体信息并建立关联
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                corporateClientDao.insertSelectiveAndBackId(corporateClient);
                user.setRelatedEntityId(corporateClient.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                individualClientDao.insertSelectiveAndBackId(individualClient);
                user.setRelatedEntityId(individualClient.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                lawyerDao.insertSelectiveAndBackId(lawyer);
                user.setRelatedEntityId(lawyer.getId());
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                administratorDao.insertSelectiveAndBackId(administrator);
                user.setRelatedEntityId(administrator.getId());
            }
            // 更新用户信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);
        } else {
            // 更新用户信息到数据库
            userDao.updateSelectiveByPrimaryKey(user);

            // 根据用户类型更新关联实体信息
            if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_CORPORATE)) {
                corporateClientDao.updateSelectiveByPrimaryKey(corporateClient);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_INDIVIDUAL)) {
                individualClientDao.updateSelectiveByPrimaryKey(individualClient);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_LAWYER)) {
                lawyerDao.updateSelectiveByPrimaryKey(lawyer);
            } else if (CommonUtil.equals(userPageVo.getUserVo().getUserType(), UserContant.USERTYPE_ADMIN)) {
                administratorDao.updateSelectiveByPrimaryKey(administrator);
            }
        }

        // 将更新后的信息复制回 userPageVo 中
        CommonUtil.copyProperties(user, userPageVo.getUserVo());
        CommonUtil.copyProperties(corporateClient, userPageVo.getCorporateClientVo());
        CommonUtil.copyProperties(individualClient, userPageVo.getIndividualClientVo());
        CommonUtil.copyProperties(lawyer, userPageVo.getLawyerVo());
        CommonUtil.copyProperties(administrator, userPageVo.getAdministratorVo());
        // 设置操作结果为成功
        userPageVo.setResult(true);
    }

    /**
     * 获取个人用户详情信息
     *
     * @param userId 用户ID
     * @return 个人用户详情信息
     */
    public IndividualDetailsVo getIndividualDetails(Long userId) {
        IndividualDetailsVo individualDetailsVo = new IndividualDetailsVo();

        // 获取用户基本信息
        User user = userDao.selectByPrimaryKey(userId);
        if (user == null) {
            return individualDetailsVo;
        }

        // 获取个人客户信息
        IndividualClient individualClient = individualClientDao.selectByPrimaryKey(user.getRelatedEntityId());
        if (individualClient == null) {
            return individualDetailsVo;
        }

        // 填充用户信息
        individualDetailsVo.setUserId(user.getId());
        individualDetailsVo.setUsername(user.getUsername());
        individualDetailsVo.setNickName(user.getNickName());
        individualDetailsVo.setEmail(user.getEmail());
        individualDetailsVo.setPhoneNumber(user.getPhoneNumber());
        individualDetailsVo.setCreateTime(user.getCreateTime());

        // 填充个人客户信息
        individualDetailsVo.setIndividualId(individualClient.getId());
        individualDetailsVo.setFullName(individualClient.getFullName());
        individualDetailsVo.setGender(individualClient.getGender());
        individualDetailsVo.setBirthDate(individualClient.getBirthDate());
        individualDetailsVo.setIsValidFlag(individualClient.getIsValidFlag());

        // 获取并填充客户服务信息
        CustomerServiceInfo serviceInfo = customerServiceInfoDao.selectByUserId(userId);
        if (serviceInfo != null) {
            individualDetailsVo.setRemainingServiceCount(serviceInfo.getRemainingServiceCount());
            individualDetailsVo.setRemainingServiceMinutes(serviceInfo.getRemainingServiceMinutes());
            individualDetailsVo.setServiceInfoUpdateTime(serviceInfo.getUpdateTime());
            individualDetailsVo.setServiceLevel(serviceInfo.getServiceLevel());
            // 设置服务开始和到期时间
            individualDetailsVo.setServiceStartTime(serviceInfo.getServiceStartTime());
            individualDetailsVo.setServiceExpireTime(serviceInfo.getServiceExpireTime());
        } else {
            // 设置默认值
            individualDetailsVo.setRemainingServiceCount(0);
            individualDetailsVo.setRemainingServiceMinutes(0);
            individualDetailsVo.setServiceLevel(1); // 默认基础级别
            // 设置默认的服务开始和到期时间
            Date now = new Date();
            individualDetailsVo.setServiceStartTime(now);
            // 默认设置到期时间为当前时间后一年
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.YEAR, 1);
            individualDetailsVo.setServiceExpireTime(calendar.getTime());
        }

        return individualDetailsVo;
    }

    /**
     * 获取法人用户详情信息
     *
     * @param userId 用户ID
     * @return 法人用户详情信息
     */
    public CorporateDetailsVo getCorporateDetails(String userId) {
        CorporateDetailsVo corporateDetailsVo = new CorporateDetailsVo();

        // 获取用户基本信息
        User user = userDao.selectByPrimaryKey(Long.valueOf(userId));
        if (user == null) {
            return corporateDetailsVo;
        }

        // 获取法人客户信息
        CorporateClient corporateClient = corporateClientDao.selectByPrimaryKey(user.getRelatedEntityId());
        if (corporateClient == null) {
            return corporateDetailsVo;
        }

        // 填充用户信息
        corporateDetailsVo.setUserId(user.getId());
        corporateDetailsVo.setUsername(user.getUsername());
        corporateDetailsVo.setNickName(user.getNickName());
        corporateDetailsVo.setEmail(user.getEmail());
        corporateDetailsVo.setPhoneNumber(user.getPhoneNumber());
        corporateDetailsVo.setCreateTime(user.getCreateTime());

        // 填充法人客户信息
        corporateDetailsVo.setCorporateId(corporateClient.getId());
        corporateDetailsVo.setCompanyName(corporateClient.getCompanyName());
        corporateDetailsVo.setCertificateType(corporateClient.getCertificateType());
        corporateDetailsVo.setCertificateNumber(corporateClient.getCertificateNumber());
        corporateDetailsVo.setContactPerson(corporateClient.getContactPerson());
        corporateDetailsVo.setIsValidFlag(corporateClient.getIsValidFlag());

        // 获取并填充客户服务信息
        CustomerServiceInfo serviceInfo = customerServiceInfoDao.selectByUserId(Long.valueOf(userId));
        if (serviceInfo != null) {
            corporateDetailsVo.setRemainingServiceCount(serviceInfo.getRemainingServiceCount());
            corporateDetailsVo.setRemainingServiceMinutes(serviceInfo.getRemainingServiceMinutes());
            corporateDetailsVo.setServiceInfoUpdateTime(serviceInfo.getUpdateTime());
            corporateDetailsVo.setServiceLevel(serviceInfo.getServiceLevel());
            corporateDetailsVo.setMaxEmployeeCount(serviceInfo.getMaxEmployeeCount());
            // 设置服务开始和到期时间
            corporateDetailsVo.setServiceStartTime(serviceInfo.getServiceStartTime());
            corporateDetailsVo.setServiceExpireTime(serviceInfo.getServiceExpireTime());
        } else {
            // 设置默认值
            corporateDetailsVo.setRemainingServiceCount(0);
            corporateDetailsVo.setRemainingServiceMinutes(0);
            corporateDetailsVo.setServiceLevel(1); // 默认基础级别
            corporateDetailsVo.setMaxEmployeeCount(5); // 默认员工数量上限
            // 设置默认的服务开始和到期时间
            Date now = new Date();
            corporateDetailsVo.setServiceStartTime(now);
            // 默认设置到期时间为当前时间后一年
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.YEAR, 1);
            corporateDetailsVo.setServiceExpireTime(calendar.getTime());
        }

        return corporateDetailsVo;
    }
}
