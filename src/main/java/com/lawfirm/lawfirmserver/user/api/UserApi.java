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

    @RequestMapping(value = "/validateLogin", method = {RequestMethod.POST})
    public UserPageVo validateLogin(@RequestBody UserPageVo userPageVo, HttpServletRequest request) {

        Boolean result = userService.validateLogin(userPageVo.getUsername(), userPageVo.getPassword());
        userPageVo.setResult(result);
        return userPageVo;
    }

}
