package com.lawfirm.lawfirmserver.image.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 影像存储实体类
 * 对应表：imageStorage
 */
@Data
public class ImageStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 影像唯一标识，自增主键
     */
    private Long imageId;
    
    /**
     * 关联的用户ID
     */
    private Long userId;
    
    /**
     * 影像类型，如合同、证明、头像等
     */
    private String imageType;
    
    /**
     * 影像的二进制数据
     */
    private byte[] imageData;

    
    /**
     * 影像记录插入时间
     */
    private Date insertTimeForHis;
    
    /**
     * 影像记录更新时间
     */
    private Date operateTimeForHis;
} 