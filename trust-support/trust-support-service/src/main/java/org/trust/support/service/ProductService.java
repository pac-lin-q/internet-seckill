package org.trust.support.service;

import org.trust.support.dto.ProductInfoDTO;
import org.trust.support.dto.Result;

public interface ProductService {
    /**
     * 创建商品
     * @param productInfoDTO
     * @return
     */
    Result<Integer> createProduct(ProductInfoDTO productInfoDTO);


    /**
     * 查询商品
     * @param productId
     * @return
     */
    Result<ProductInfoDTO> queryProduct(String productId);
}
