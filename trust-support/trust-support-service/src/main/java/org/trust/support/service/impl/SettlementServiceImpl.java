package org.trust.support.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dao.ActivityDao;
import org.trust.support.dao.OrderRecordDao;
import org.trust.support.dao.ProductInfoDao;
import org.trust.support.dto.SettlementDataDTO;
import org.trust.support.dto.SettlementDataRequestDTO;
import org.trust.support.dto.SettlementOrderDTO;
import org.trust.support.entity.ActivityInfo;
import org.trust.support.entity.OrderRecord;
import org.trust.support.entity.ProductInfo;
import org.trust.support.exception.TrustException;
import org.trust.support.service.SettlementService;
import org.trust.support.tools.RedisTools;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    OrderRecordDao orderRecordDao;

    @Autowired
    ActivityDao activityDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    RedisTools redisTools;

    @Override
    public String submitOrder(SettlementOrderDTO orderDTO) throws TrustException {
        //1、校验商品标识
        ProductInfo productInfo = productInfoDao.selectByProductId(orderDTO.getProductId());
        if (productInfo!=null && productInfo.getTag()!=2){
            throw new TrustException("商品标识错误");
        }
        //限购
        Long count= redisTools.evalsha("Store"+orderDTO.getProductId(),String.valueOf(orderDTO.getBuyNum()));
        log.error(orderDTO.getUserId()+"限购结果："+count);
        if (count==null|count<=0){
            return null;
        }
        //3.下单
        Random random = new Random(10000);
        String orderId = String.valueOf(System.currentTimeMillis())+random.nextInt();
        OrderRecord orderRecord = new OrderRecord();
        ActivityInfo activityInfo = activityDao.selectByProductId(orderDTO.getProductId());

        orderRecord.setOrderId(orderId);
        BigDecimal orderPrice = activityInfo.getActivityPrice().multiply(new BigDecimal(orderDTO.getBuyNum()));
        orderRecord.setOrderPrice(orderPrice);
        orderRecord.setOrderStatus(0);
        orderRecord.setAddress(orderDTO.getAddress());
        orderRecord.setPayType(orderDTO.getPayType());
        orderRecord.setProductId(orderDTO.getProductId());
        orderRecord.setUserId(orderDTO.getUserId());
        orderRecord.setOrderTime(new Date());
        orderRecord.setBuyNum(orderDTO.getBuyNum());
        orderRecordDao.insert(orderRecord);

        //预占库存
        activityDao.updateStockNum(activityInfo.getId(),orderDTO.getBuyNum());
        //更新订单，下单成功
        orderRecordDao.updateOrderStatus(orderId,1);
        return orderId;
    }

    @Override
    public SettlementDataDTO settlementData(SettlementDataRequestDTO requestDTO) {
        ActivityInfo activityInfo=activityDao.selectByProductId(requestDTO.getProductId());
        SettlementDataDTO settlementDataDTO = new SettlementDataDTO();
        settlementDataDTO.setAssets("");
        settlementDataDTO.setPayType(1);//在线支付
        settlementDataDTO.setTotalPrice(activityInfo.getActivityPrice().multiply(new BigDecimal(requestDTO.getBuyNum())));
        settlementDataDTO.setAddress("上海市疫情防空区");
        return settlementDataDTO;
    }
}
