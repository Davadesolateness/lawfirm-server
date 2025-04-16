package com.lawfirm.lawfirmserver.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Web工具类
 */
public class WebUtils {

    /**
     * 获取请求IP地址
     *
     * @return IP地址
     */
    public static String getRequestIp() {
        return getRequest().map(WebUtils::getIpAddress).orElse("unknown");
    }

    /**
     * 获取设备信息
     *
     * @return 设备信息
     */
    public static String getDeviceInfo() {
        return getRequest().map(request -> request.getHeader("User-Agent")).orElse("unknown");
    }

    /**
     * 获取当前请求
     *
     * @return 当前请求
     */
    private static Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest());
    }

    /**
     * 获取IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，取第一个IP为客户端IP
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }
        return ip;
    }

    /**
     * 判断IP是否无效
     *
     * @param ip IP地址
     * @return 是否无效
     */
    private static boolean isInvalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
} 