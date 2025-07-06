package com.lawfirm.lawfirmserver.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common.vo
 * @className: BannerVO
 * @author: Eric
 * @description: 轮播图信息视图对象
 * @date: 2025/6/21 10:15
 * @version: 1.0
 */
@Data
@ApiModel("轮播图信息")
public class BannerVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("轮播图ID")
    private Long id;

    @ApiModelProperty("轮播图标题")
    private String title;

    @ApiModelProperty("轮播图图片URL")
    private String imageUrl;

    @ApiModelProperty("轮播图链接URL")
    private String linkUrl;

    @ApiModelProperty("排序序号")
    private Integer sortOrder;

    @ApiModelProperty("轮播图描述")
    private String description;

    public BannerVo() {
    }

    public BannerVo(Long id, String title, String imageUrl, String linkUrl, Integer sortOrder, String description) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.sortOrder = sortOrder;
        this.description = description;
    }
} 