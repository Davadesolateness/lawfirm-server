package com.lawfirm.lawfirmserver.lawyer.api;

import com.lawfirm.lawfirmserver.lawyer.service.LawyerService;
import com.lawfirm.lawfirmserver.lawyer.vo.LawyerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/lawyer")
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
    @RequestMapping(value = "/updateLawyer", method = {RequestMethod.POST})
    public LawyerVo updateLawyer(@RequestBody LawyerVo lawyerVo, HttpServletRequest request) {
        // 调用lawyerService的updateLawyer方法，将传入的lawyerVo中的律师信息更新到数据库中
        lawyerService.updateLawyer(lawyerVo);
        // 调用lawyerService的getLawyer方法，根据lawyerVo中的律师ID从数据库中获取更新后的律师信息
        lawyerVo = lawyerService.getLawyer(lawyerVo.getId());
        return lawyerVo;
    }
}
