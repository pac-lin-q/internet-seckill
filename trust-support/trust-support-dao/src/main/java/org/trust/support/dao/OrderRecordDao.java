package org.trust.support.dao;

import org.apache.ibatis.annotations.Param;
import org.trust.support.entity.OrderRecord;

public interface OrderRecordDao {
    /**
     * 创建订单
     * @param orderRecord
     * @return
     */
    int insert(OrderRecord orderRecord);

    /**
     * 查询活动
     * @param
     * @return
     */
    OrderRecord selectByOrderId(String orderId);

    /**
     * 更新状态
     * @param
     * @return
     */
    int updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus);
}
