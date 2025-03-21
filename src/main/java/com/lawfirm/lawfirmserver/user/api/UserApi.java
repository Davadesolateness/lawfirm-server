package com.lawfirm.lawfirmserver.user.api;

import com.lawfirm.lawfirmserver.user.service.UserService;
import com.lawfirm.lawfirmserver.user.vo.UserPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private UserService userService;

    /**
     * 处理用户登录验证的请求。
     * 该方法接收一个包含用户登录信息的 UserPageVo 对象，并调用 UserService 中的验证方法进行验证。
     * 验证结果会被设置到 UserPageVo 对象中，并将该对象作为响应返回。
     *
     * @param userPageVo 包含用户登录信息的 UserPageVo 对象，通过请求体传入。
     *                   其中 UserPageVo 内部的 UserVo 对象包含用户名和密码信息。
     * @param request    HttpServletRequest 对象，用于获取 HTTP 请求的相关信息，但在本方法中暂未使用。
     * @return 返回包含登录验证结果的 UserPageVo 对象。
     * 若验证成功，UserPageVo 的 result 属性为 true；若验证失败，result 属性为 false。
     */
    @RequestMapping(value = "/validateLogin", method = {RequestMethod.POST})
    public UserPageVo validateLogin(@RequestBody UserPageVo userPageVo, HttpServletRequest request) {
        // 调用 UserService 的 validateLogin 方法，传入用户名和密码进行登录验证
        // 并将验证结果存储在 result 变量中
        Boolean result = userService.validateLogin(userPageVo.getUserVo().getUsername(), userPageVo.getUserVo().getPassword());
        // 将验证结果设置到 UserPageVo 对象的 result 属性中
        userPageVo.setResult(result);
        // 返回包含验证结果的 UserPageVo 对象作为响应
        return userPageVo;
    }

    /**
     * 处理保存或更新用户信息的请求。
     * 该方法接收一个包含用户信息的 UserPageVo 对象，
     * 并调用 UserService 中的方法将用户信息保存或更新到数据库中，
     * 最后将传入的 UserPageVo 对象原样返回。
     *
     * @param userPageVo 包含用户信息的 UserPageVo 对象，通过请求体传入。
     *                   该对象包含了需要保存或更新的用户相关数据。
     * @param request    HttpServletRequest 对象，用于获取 HTTP 请求的相关信息，
     *                   不过在本方法中暂未使用此对象。
     * @return 返回传入的包含用户信息的 UserPageVo 对象，方便客户端确认处理的对象。
     */
    @RequestMapping(value = "/saveuser", method = {RequestMethod.POST})
    public UserPageVo saveUser(@RequestBody UserPageVo userPageVo, HttpServletRequest request) {
        // 调用 UserService 的 saveOrUpdateUser 方法，将 UserPageVo 对象传递进去
        // 该方法会将用户信息保存或更新到数据库中
        userService.saveOrUpdateUser(userPageVo);
        // 将传入的 UserPageVo 对象原样返回，便于客户端确认处理的用户信息
        return userPageVo;
    }

}
