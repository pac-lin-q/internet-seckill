package org.trust.support.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Data
public class OrderRecord {
    private long id;
    private String orderId;
    private String productId;
    private String userId;
    private Integer buyNum;
    private BigDecimal orderPrice;
    private String address;
    private Integer payType;
    private Date orderTime;
    private Integer orderStatus; //订单状态  0：初始化，1：成功待支付，2：完成，3：处理中，4：失败，5：订单取消
}
