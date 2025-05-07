package com.lawfirm.lawfirmserver.image.vo;

import lombok.Data;

/**
 * 图片上传结果VO
 *
 * @author dong
 */
@Data
public class ImageUploadResult {
    /**
     * 图片访问URL
     */
    private String url;

    /**
     * 图片MIME类型
     */
    private String type;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 图片大小（字节）
     */
    private Long size;

    /**
     * 图片数据（Base64编码）
     */
    private String imageData;


} 