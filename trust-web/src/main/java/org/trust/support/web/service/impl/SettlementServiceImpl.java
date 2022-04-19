package org.trust.support.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trust.support.constant.ResultCodeConstant;
import org.trust.support.dto.*;
import org.trust.support.export.ActivityExportService;
import org.trust.support.export.SettlementExportService;
import org.trust.support.web.common.TrustException;
import org.trust.support.web.model.SettlementInitDTO;
import org.trust.support.web.model.SettlementSubmitDto;
import org.trust.support.web.service.SettlementService;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    ActivityExportService activityExportService;

    @Autowired
    SettlementExportService settlementExportService;

    @Override
    public SettlementInitDTO initData(String productId, String buyNum) throws TrustException {
        SettlementInitDTO initDTO = new SettlementInitDTO();

        //1、校验活动的有效性
        Result<SeckillActivityDTO> activityResult = activityExportService.queryActivity(productId);
        if (activityResult == null){
            throw new TrustException("系统异常");
        }
        if (StringUtils.endsWithIgnoreCase(activityResult.getCode(), ResultCodeConstant.SUCCESS)){
            throw new TrustException(activityResult.getMessage());
        }
        SeckillActivityDTO seckillActivityDTO = activityResult.getData();
        //校验活动是否有效
        Date now = new Date();
        if (now.before(seckillActivityDTO.getActivityStart())||now.after(seckillActivityDTO.getActivityEnd())){
            throw new TrustException("活动不存在或活动已结束");
        }
        //校验是否超购
        if (Integer.parseInt(buyNum)>seckillActivityDTO.getLimitNum()){
            throw new TrustException("您超过了单次购买数量！");
        }
        //校验库存
        if (Integer.parseInt(buyNum)>seckillActivityDTO.getStockNum()){
            throw new TrustException("您所购买的商品已售完！");
        }
        //构建活动商品信息
        initDTO.setLimitNum(String.valueOf(seckillActivityDTO.getLimitNum()));
        initDTO.setActivityName(seckillActivityDTO.getActivityName());
        initDTO.setProductPrice(seckillActivityDTO.getActivityPrice().toPlainString());
        initDTO.setProductPictureUrl(seckillActivityDTO.getActivityPictureUrl());
        initDTO.setProductId(seckillActivityDTO.getProductId());
        //调用接口初始化
        SettlementDataRequestDTO requestDTO = new SettlementDataRequestDTO();
        requestDTO.setUserId(UUID.randomUUID().toString());
        requestDTO.setBuyNum(Integer.parseInt(buyNum));
        requestDTO.setProductId(productId);

        Result<SettlementDataDTO> dataDTOResult = settlementExportService.settlementData(requestDTO);
        if (dataDTOResult==null){
            throw new TrustException("系统异常");
        }
        SettlementDataDTO settlementDataDTO = dataDTOResult.getData();
        //设置结算元素，后续可根据需求要求增加对应的优惠券，红包，是否需要发票等等内容
        initDTO.setTotalPrice(settlementDataDTO.getTotalPrice().toPlainString());
        initDTO.setPayType(String.valueOf(settlementDataDTO.getPayType()));
        initDTO.setAddress(settlementDataDTO.getAddress());
        return initDTO;
    }

    @Override
    public Map<String, Object> dependency() {
        return null;
    }

    @Override
    public SettlementSubmitDto submitOrder(SettlementOrderDTO settlementOrderDTO) throws TrustException {
        return null;
    }
}
