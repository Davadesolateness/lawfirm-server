package com.lawfirm.lawfirmserver.image.util;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.image.util
 * @className: ImageUtil
 * @author: Eric
 * @description: TODO
 * @date: 2025/5/3 17:52
 * @version: 1.0
 */
public class ImageUtil {


    /**
     * 根据文件扩展名获取MIME类型
     *
     * @param extension 文件扩展名
     * @return MIME类型
     */
    public static String getMimeTypeFromExtension(String extension) {
        if (extension == null) {
            // 默认MIME类型
            return "image/jpeg";
        }
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "webp":
                return "image/webp";
            default:
                // 默认MIME类型
                return "image/jpeg";
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名（不含点号）
     */
    public static String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            // 默认格式
            return "jpeg";
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            String extension = filename.substring(dotIndex + 1).toLowerCase();
            // 只返回常见图片格式，其他格式默认为jpeg
            if (extension.equals("png") || extension.equals("jpg") ||
                    extension.equals("jpeg") || extension.equals("gif")) {
                return extension;
            }
        }
        // 默认格式
        return "jpeg";
    }

}
