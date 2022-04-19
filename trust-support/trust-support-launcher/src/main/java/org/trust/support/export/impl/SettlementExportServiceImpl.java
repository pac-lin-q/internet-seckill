package org.trust.support.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dao.OrderRecordDao;
import org.trust.support.dto.Result;
import org.trust.support.dto.SettlementDataDTO;
import org.trust.support.dto.SettlementDataRequestDTO;
import org.trust.support.dto.SettlementOrderDTO;
import org.trust.support.entity.OrderRecord;
import org.trust.support.export.SettlementExportService;
import org.trust.support.service.ActivityService;
import org.trust.support.service.SettlementService;

@Service
@Slf4j
public class SettlementExportServiceImpl implements SettlementExportService {

    @Autowired
    SettlementService settlementService;

    @Autowired
    ActivityService activityService;

    @Autowired
    OrderRecordDao orderRecordDao;

    @Override
    public Result<SettlementDataDTO> settlementData(SettlementDataRequestDTO requestDTO) {
        SettlementDataDTO settlementDataDTO = settlementService.settlementData(requestDTO);
        return new Result<>(settlementDataDTO);
    }

    @Override
    public Result<String> submitOrder(SettlementOrderDTO orderDTO) {
        try {
            String orderId = settlementService.submitOrder(orderDTO);
            return new Result<>(orderId);
        }catch (Exception e){
            log.error("发生异常了："+e);
        }
        return new Result<>(null);
    }

    @Override
    public Result<String> getPayPageUrl(String orderId) {
        OrderRecord orderRecord = orderRecordDao.selectByOrderId(orderId);
        String payPageUrl = "http://localhost:8080/mock/payPage?orderId="+orderId+"&orderPrice="+orderRecord.getOrderPrice().toPlainString();
        return new Result<>(payPageUrl);
    }
}
