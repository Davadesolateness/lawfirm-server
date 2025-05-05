package com.lawfirm.lawfirmserver.lawyer.api;

import com.lawfirm.lawfirmserver.common.PageResult;
import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.lawyer.service.LawyerService;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerSearchVo;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lawyer")
@Api(tags = "律师管理接口", description = "提供律师信息的增删改查服务")
public class LawyerApi {

    @Autowired
    LawyerService lawyerService;

    /**
     * 处理更新律师信息的请求，并返回更新后的律师信息。
     * <p>
     * 该方法通过`@RequestMapping`注解映射到"/updateLawyer"路径，并且只处理HTTP的POST请求。
     * 它接受一个包含律师信息的{@link LawyerVo}对象作为请求体（通过{@link RequestBody}注解），
     * 同时接受一个{@link HttpServletRequest}对象，用于获取请求相关的信息（虽然目前代码中未使用）。
     * <p>
     * 在方法内部，首先调用{@link LawyerService}中的`updateLawyer`方法，
     * 将传入的{@link LawyerVo}对象中的律师信息更新到数据库中。
     * 然后再次调用{@link LawyerService}中的`getLawyer`方法，
     * 根据更新后的{@link LawyerVo}对象中的律师ID从数据库中获取最新的律师信息，
     * 并将其重新赋值给`lawyerVo`变量。
     * <p>
     * 最后，将更新后的{@link LawyerVo}对象返回给客户端，
     * 以便客户端获取更新后的律师信息。
     *
     * @param lawyerVo 包含律师信息的视图对象，通过请求体传入，将用于更新数据库中的律师信息
     * @param request  {@link HttpServletRequest}对象，可用于获取请求相关的信息，如请求头、请求参数等
     * @return 更新后的{@link LawyerVo}对象，包含了最新的律师信息
     */
    @ApiOperation(value = "更新律师信息", notes = "根据传入的律师信息更新数据库中的律师记录，并返回更新后的完整律师信息")
    @PostMapping("/updateLawyer")
    public Result<LawyerVo> updateLawyer(@RequestBody @Valid LawyerVo lawyerVo, HttpServletRequest request) {
        try {
            // 调用lawyerService的updateLawyer方法，将传入的lawyerVo中的律师信息更新到数据库中
            lawyerService.updateLawyer(lawyerVo);
            // 调用lawyerService的getLawyer方法，根据lawyerVo中的律师ID从数据库中获取更新后的律师信息
            lawyerVo = lawyerService.getLawyer(lawyerVo.getId());
            return Result.success(lawyerVo);
        } catch (Exception e) {
            return Result.fail("更新律师信息失败: " + e.getMessage());
        }
    }

    /**
     * 处理获取律师信息的请求，并返回相应的律师信息视图对象。
     * <p>
     * 该方法通过 @RequestMapping 注解映射到 "/getLawyerInfo" 路径，
     * 并且只处理 HTTP 的 POST 请求。
     * 它接受一个包含律师信息的 LawyerVo 对象作为请求体（通过 @RequestBody 注解），
     * 同时接受一个 HttpServletRequest 对象，用于获取请求相关的信息（虽然目前代码中未使用）。
     * <p>
     * 在方法内部，调用 lawyerService 的 getLawyer 方法，
     * 根据传入的 lawyerVo 对象中的律师 ID，从数据库中查询出对应的律师信息，
     * 并将查询结果重新赋值给 lawyerVo 变量。
     * <p>
     * 最后，将包含律师信息的 lawyerVo 对象返回给客户端，
     * 以便客户端获取该律师的详细信息。
     *
     * @param lawyerVo 包含律师 ID 等信息的视图对象，通过请求体传入，用于指定要获取信息的律师
     * @param request  HttpServletRequest 对象，可用于获取请求相关的信息，如请求头、请求参数等
     * @return 包含律师详细信息的 LawyerVo 对象，从数据库中查询得到并返回给客户端
     */
    @ApiOperation(value = "获取律师信息", notes = "根据传入的律师ID查询律师的详细信息")
    @PostMapping("/getLawyerInfo")
    public Result<LawyerVo> getLawyerInfo(@RequestBody @Valid LawyerVo lawyerVo, HttpServletRequest request) {
        try {
            // 调用 lawyerService 的 getLawyer 方法，根据 lawyerVo 中的律师 ID 从数据库中获取律师信息
            lawyerVo = lawyerService.getLawyer(lawyerVo.getId());
            if (lawyerVo == null) {
                return Result.fail("未找到律师信息");
            }
            // 将获取到律师信息的 lawyerVo 对象返回给客户端
            return Result.success(lawyerVo);
        } catch (Exception e) {
            return Result.fail("获取律师信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取所有律师的信息列表。
     *
     * @return 包含所有律师信息的 List<LawyerVo> 对象列表
     */
    @ApiOperation(value = "获取所有律师列表", notes = "返回系统中所有律师的基本信息列表")
    @GetMapping("/getAllLawyers")
    public Result<List<LawyerVo>> getAllLawyers() {
        try {
            List<LawyerVo> lawyers = lawyerService.getAllLawyers();
            return Result.success(lawyers);
        } catch (Exception e) {
            return Result.fail("获取律师列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据律师 ID 获取律师信息。
     *
     * @param id 律师的唯一标识 ID，作为路径变量传入
     * @return 包含律师详细信息的 LawyerVo 对象
     */
    @ApiOperation(value = "根据ID获取律师信息", notes = "通过路径变量传入的律师ID获取单个律师的详细信息")
    @GetMapping("/getLawyerById")
    public Result<LawyerVo> getLawyerById(
            @ApiParam(value = "律师ID", required = true, example = "1")
            @RequestParam(value = "id") String id) {
        try {
            LawyerVo lawyer = lawyerService.getLawyer(Long.valueOf(id));
            if (lawyer == null) {
                return Result.fail("未找到律师信息");
            }
            return Result.success(lawyer);
        } catch (Exception e) {
            return Result.fail("获取律师信息失败: " + e.getMessage());
        }
    }

    /**
     * 搜索律师列表，支持分页和多条件筛选
     *
     * @param params 搜索参数，包含关键词、专业领域、页码和每页大小等
     * @return 包含律师列表和总条数的结果对象
     */
    @ApiOperation(value = "搜索律师列表", notes = "根据关键词、专业领域等条件分页搜索律师信息")
    @PostMapping("/searchLawyers")
    public Result<PageResult<LawyerVo>> searchLawyers(@RequestBody @Valid LawyerSearchVo params) {
        try {
            log.info("搜索律师列表: 页码={}, 每页大小={}, 关键词={}, 专业领域={}",
                    params.getPage(), params.getPageSize(), params.getKeyword(), params.getSpecialty());

            PageResult<LawyerVo> result = lawyerService.searchLawyers(params);

            log.info("搜索律师列表成功: 当前页={}, 总条数={}, 结果数量={}",
                    result.getCurrent(), result.getTotal(), result.getData().size());

            return Result.success(result);
        } catch (Exception e) {
            log.error("搜索律师列表失败: ", e);
            return Result.fail("搜索律师列表失败: " + e.getMessage());
        }
    }
}
