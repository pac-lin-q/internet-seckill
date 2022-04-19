package org.trust.support.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dto.ProductInfoDTO;
import org.trust.support.dto.Result;
import org.trust.support.export.ProductExportService;
import org.trust.support.service.ProductService;

@Service
@Slf4j
public class ProductExportServiceImpl implements ProductExportService {

    @Autowired
    ProductService productService;

    @Override
    public Result<Integer> createProduct(ProductInfoDTO productInfoDTO) {
        return null;
    }

    @Override
    public Result<ProductInfoDTO> queryProduct(String productId) {
        return productService.queryProduct(productId);
    }
}
