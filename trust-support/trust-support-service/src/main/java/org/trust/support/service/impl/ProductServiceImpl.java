package org.trust.support.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dao.ProductInfoDao;
import org.trust.support.dto.ProductInfoDTO;
import org.trust.support.dto.Result;
import org.trust.support.entity.ProductInfo;
import org.trust.support.service.ProductService;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public Result<Integer> createProduct(ProductInfoDTO productInfoDTO) {
        ProductInfo productInfo =new ProductInfo();
        BeanUtils.copyProperties(productInfoDTO,productInfo);
        int count = productInfoDao.insert(productInfo);
        BeanUtils.copyProperties(productInfoDTO,productInfo);
        return new Result<>(count);
    }

    @Override
    public Result<ProductInfoDTO> queryProduct(String productId) {
        ProductInfo productInfo=productInfoDao.selectByProductId(productId);
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        BeanUtils.copyProperties(productInfo,productInfoDTO);
        return new Result<>(productInfoDTO);
    }
}
