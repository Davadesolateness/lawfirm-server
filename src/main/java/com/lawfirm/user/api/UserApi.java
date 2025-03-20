package com.lawfirm.user.api;

import com.lawfirm.user.service.UserService;
import com.lawfirm.user.vo.UserPageVo;
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

    @RequestMapping(value = "/checkUser", method = {RequestMethod.POST})
    public UserPageVo checkUser(@RequestBody UserPageVo userPageVo, HttpServletRequest request) {

        Boolean result = userService.checkUser(userPageVo.getUsername(), userPageVo.getPassword());
        userPageVo.setResult(result);
        return userPageVo;
    }

}
