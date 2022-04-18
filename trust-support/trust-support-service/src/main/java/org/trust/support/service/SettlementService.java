package org.trust.support.service;

import org.trust.support.dto.SettlementDataDTO;
import org.trust.support.dto.SettlementDataRequestDTO;
import org.trust.support.dto.SettlementOrderDTO;
import org.trust.support.exception.TrustException;

public interface SettlementService {
    /**
     * 下单
     * @param orderDTO
     * @return 订单号
     */
    String submitOrder(SettlementOrderDTO orderDTO) throws TrustException;


    SettlementDataDTO settlementData(SettlementDataRequestDTO requestDTO);
}
