package com.lawfirm.lawfirmserver.admin.api;

import com.lawfirm.lawfirmserver.admin.po.Administrator;
import com.lawfirm.lawfirmserver.admin.service.AdminService;
import com.lawfirm.lawfirmserver.admin.vo.AdministratorDetailVo;
import com.lawfirm.lawfirmserver.admin.vo.AdministratorVo;
import com.lawfirm.lawfirmserver.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员相关API接口
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理", description = "管理员信息相关接口")
public class AdminApi {
    @Autowired
    private AdminService adminService;

    /**
     * 根据管理员ID获取管理员详细信息
     * 该方法通过管理员ID查询管理员的详细资料信息
     *
     * @param id 管理员ID，必须提供
     * @return 包含管理员详细信息的Result对象
     */
    @GetMapping("/getAdministratorById")
    @ApiOperation("根据ID获取管理员信息")
    public Result<AdministratorDetailVo> getAdministratorById(
            @ApiParam(value = "管理员ID", required = true, example = "1")
            @RequestParam(value = "id") String id) {
        AdministratorDetailVo administratorDetailVo = adminService.getAdministratorById(id);

        if (administratorDetailVo == null) {
            return Result.fail("未找到管理员信息");
        }

        return Result.success("获取管理员信息成功", administratorDetailVo);
    }
    
    /**
     * 根据管理员用户名获取管理员详细信息
     * 该方法通过管理员用户名查询管理员的详细资料信息
     *
     * @param adminName 管理员用户名，必须提供
     * @return 包含管理员详细信息的Result对象
     */
    @GetMapping("/apiAdministratorByName")
    @ApiOperation("根据用户名获取管理员信息")
    public Result<AdministratorVo> getAdministratorByName(
            @ApiParam(value = "管理员用户名", required = true, example = "admin")
            @RequestParam String adminName) {
        AdministratorVo administratorVO = adminService.getAdministratorByName(adminName);

        if (administratorVO == null) {
            return Result.fail("未找到管理员信息");
        }

        return Result.success("获取管理员信息成功", administratorVO);
    }
    
    /**
     * 创建新管理员
     * 该方法通过接收管理员信息创建新的管理员记录
     *
     * @param administrator 管理员信息对象，包含管理员的详细信息
     * @return 包含新创建管理员ID的Result对象
     */
    @PostMapping("/createAdministrator")
    @ApiOperation("创建新管理员")
    public Result<Long> createAdministrator(@RequestBody Administrator administrator) {
        try {
            Long administratorId = adminService.createAdministrator(administrator);
            return Result.success("管理员创建成功", administratorId);
        } catch (Exception e) {
            return Result.fail("管理员创建失败：" + e.getMessage());
        }
    }
}