package com.lawfirm.lawfirmserver.common.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;

public class CommonUtil {


    /**
     * 比较输入的字符串1和字符串2是否相等
     *
     * @param str1 ：字符串1
     * @param str2 ：字符串2
     * @return 相等：true，不相等：false
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == str2) {
            return true;
        }
        // null 和 空串在系统中认为相等
        str1 = (str1 == null ? "" : str1.trim());
        str2 = (str2 == null ? "" : str2.trim());
        return str1.equals(str2);
    }

    /**
     * 对密码进行加密
     *
     * @param plainPassword 明文密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * 验证密码是否匹配
     *
     * @param plainPassword  明文密码
     * @param hashedPassword 加密后的密码
     * @return 如果匹配返回 true，否则返回 false
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * 使用 Spring 的 BeanUtils 实现对象属性拷贝的公共方法
     * 此方法将源对象的属性值拷贝到目标对象中
     *
     * @param source 源对象，即属性的来源对象
     * @param target 目标对象，即要将属性复制到的对象
     * @param <S>    源对象的类型
     * @param <T>    目标对象的类型
     */
    public static <S, T> void copyProperties(S source, T target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }
}
