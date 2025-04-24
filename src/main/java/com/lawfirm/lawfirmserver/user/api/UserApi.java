package com.lawfirm.lawfirmserver.user.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.user.service.UserService;
import com.lawfirm.lawfirmserver.user.vo.CorporateDetailsVo;
import com.lawfirm.lawfirmserver.user.vo.IndividualDetailsVo;
import com.lawfirm.lawfirmserver.user.vo.UsersPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户信息相关接口")
public class UserApi {
    @Autowired
    private UserService userService;

    /**
     * 处理用户登录验证的请求。
     * 该方法接收一个包含用户登录信息的 UserPageVo 对象，并调用 UserService 中的验证方法进行验证。
     * 验证结果会被设置到 UserPageVo 对象中，并将该对象作为响应返回。
     *
     * @param usersPageVo 包含用户登录信息的 UserPageVo 对象，通过请求体传入。
     *                    其中 UserPageVo 内部的 UserVo 对象包含用户名和密码信息。
     * @param request     HttpServletRequest 对象，用于获取 HTTP 请求的相关信息，但在本方法中暂未使用。
     * @return 返回包含登录验证结果的 Result 对象。
     * 若验证成功，返回成功结果；若验证失败，返回失败结果。
     */
    @RequestMapping(value = "/validateLogin", method = {RequestMethod.POST})
    @ApiOperation("用户登录验证")
    public Result<UsersPageVo> validateLogin(@RequestBody UsersPageVo usersPageVo, HttpServletRequest request) {
        // 调用 UserService 的 validateLogin 方法，传入用户名和密码进行登录验证
        Boolean result = userService.validateLogin(usersPageVo.getUsersVo().getUsername(), usersPageVo.getUsersVo().getPassword());
        // 将验证结果设置到 UserPageVo 对象的 result 属性中
        usersPageVo.setResult(result);

        if (result) {
            return Result.success("登录成功", usersPageVo);
        } else {
            return Result.fail("用户名或密码错误");
        }
    }

    /**
     * 处理修改用户密码的请求的方法。
     * 该方法接收一个包含用户信息的 UserPageVo 对象，并从中提取用户名、旧密码和新密码，
     * 调用 UserService 中的 changePassword 方法来尝试修改用户密码。
     *
     * @param usersPageVo 包含用户信息的 UserPageVo 对象，其中应包含用户名、旧密码和新密码。
     *                    方法会从该对象的 userVo 属性中提取用户名、旧密码和新密码。
     * @param request     HttpServletRequest 对象，可用于获取请求相关的信息，如请求头、请求参数等，
     *                    本方法中暂未使用该对象，但保留以满足可能的扩展需求。
     * @return 返回包含修改密码操作结果的 Result 对象。
     * 若修改成功，返回成功结果；若修改失败，返回失败结果。
     */
    @RequestMapping(value = "/changePassword", method = {RequestMethod.POST})
    @ApiOperation("修改用户密码")
    public Result<Void> changePassword(@RequestBody UsersPageVo usersPageVo, HttpServletRequest request) {
        String username = usersPageVo.getUsersVo().getUsername();
        String oldPassword = usersPageVo.getUsersVo().getPassword();
        String newPassword = null;//userPageVo.getUserVo().getNewPassword();

        boolean result = userService.changePassword(username, oldPassword, newPassword);

        if (result) {
            return Result.success("密码修改成功", null);
        } else {
            return Result.fail("密码修改失败");
        }
    }

    /**
     * 处理保存或更新用户信息的请求。
     * 该方法接收一个包含用户信息的 UserPageVo 对象，
     * 并调用 UserService 中的方法将用户信息保存或更新到数据库中。
     *
     * @param usersPageVo 包含用户信息的 UserPageVo 对象，通过请求体传入。
     *                    该对象包含了需要保存或更新的用户相关数据。
     * @param request     HttpServletRequest 对象，用于获取 HTTP 请求的相关信息，
     *                    不过在本方法中暂未使用此对象。
     * @return 返回保存或更新结果的 Result 对象。
     */
    @RequestMapping(value = "/saveUser", method = {RequestMethod.POST})
    @ApiOperation("保存用户信息")
    public Result<UsersPageVo> saveUser(@RequestBody UsersPageVo usersPageVo, HttpServletRequest request) {
        // 调用 UserService 的 saveOrUpdateUser 方法，将 UserPageVo 对象传递进去
        // 该方法会将用户信息保存或更新到数据库中
        userService.saveOrUpdateUser(usersPageVo);

        return Result.success("用户信息保存成功", usersPageVo);
    }

    /**
     * 获取个人用户详情信息
     * 该方法通过用户ID获取个人用户的详细信息，包括User表和IndividualClient表的关联数据
     *
     * @param userId 用户ID
     * @return 包含个人用户详细信息的Result对象
     */
    @RequestMapping(value = "/getIndividualDetails/{userId}", method = {RequestMethod.GET})
    @ApiOperation("获取个人用户详情")
    public Result<IndividualDetailsVo> getIndividualDetails(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable("userId") Long userId) {
        IndividualDetailsVo details = userService.getIndividualDetails(userId);
        if (details != null && details.getUserId() != null) {
            return Result.success("获取个人用户详情成功", details);
        } else {
            return Result.fail("获取个人用户详情失败");
        }
    }

    /**
     * 获取法人用户详情信息
     * 该方法通过用户ID获取法人用户的详细信息，包括User表和CorporateClient表的关联数据
     *
     * @param userId 用户ID
     * @return 包含法人用户详细信息的Result对象
     */
    @GetMapping(value = "/getCorporateDetails")
    @ApiOperation("获取法人用户详情")
    public Result<CorporateDetailsVo> getCorporateDetails(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @RequestParam(value = "userId") String userId) {
        CorporateDetailsVo details = userService.getCorporateDetails(userId);
        if (details != null && details.getUserId() != null) {
            return Result.success("获取法人用户详情成功", details);
        } else {
            return Result.fail("获取法人用户详情失败");
        }
    }
}
