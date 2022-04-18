package org.trust.support.dao;

import org.apache.ibatis.annotations.Param;
import org.trust.support.entity.ProductInfo;

public interface ProductInfoDao {
    /**
     * 创建商品
     * @param productInfo
     * @return
     */
    int insert(ProductInfo productInfo);

    /**
     * 查询商品
     * @param id
     * @return
     */
    ProductInfo selectById(Long id);

    /**
     * 查询商品
     * @param productId
     * @return
     */
    ProductInfo selectByProductId(@Param("productId") String productId);

    /**
     * 更新商品标签 1：正常商品，2：秒杀商品 3：预约商品
     * @param productId
     * @return
     */
    int updateTag(@Param("productId") String productId,@Param("tag") Integer tag);
}
