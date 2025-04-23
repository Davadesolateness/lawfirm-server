package com.lawfirm.lawfirmserver.order.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通过ins-framework-mybatis工具自动生成，请勿手工修改。表welfaredistributioncustomer的PO对象<br/>
 * 对应表名：welfaredistributioncustomer
 */
@Data
public class WelfareDistributionCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应字段：distributionId,备注：福利发放记录唯一标识，自增主键
     */
    private Long distributionId;
    /**
     * 对应字段：orderId,备注：关联的福利发放订单 ID
     */
    private Long orderId;
    /**
     * 对应字段：userId,备注：获得福利的用户 ID
     */
    private Long userId;
    /**
     * 对应字段：insertTimeForHis,备注：记录插入时间
     */
    private Date insertTimeForHis;
    /**
     * 对应字段：operateTimeForHis,备注：记录更新时间
     */
    private Date operateTimeForHis;

}
