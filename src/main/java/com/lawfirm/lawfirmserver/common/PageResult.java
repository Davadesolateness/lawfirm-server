package com.lawfirm.lawfirmserver.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @projectName: lawfirm-server
 * @package: com.lawfirm.lawfirmserver.common
 * @className: PageResult
 * @author: Eric
 * @description: TODO
 * @date: 2025/5/1 15:46
 * @version: 1.0
 */
@Data
@ApiModel(description = "分页结果")
public class PageResult<T> {

    @ApiModelProperty(value = "数据列表")
    private List<T> data;

    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private int pages;

    @ApiModelProperty(value = "当前页")
    private int current;

    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(List<T> data, long total, int page, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setData(data);
        result.setTotal(total);
        result.setCurrent(page);

        // 计算总页数
        int pages = (int) (total / pageSize);
        if (total % pageSize != 0) {
            pages++;
        }
        result.setPages(pages);

        return result;
    }
}