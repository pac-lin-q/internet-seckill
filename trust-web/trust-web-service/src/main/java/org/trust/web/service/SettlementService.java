package org.trust.web.service;

import org.trust.support.dto.SettlementOrderDTO;
import org.trust.web.common.TrustException;
import org.trust.web.model.SettlementInitDTO;
import org.trust.web.model.SettlementSubmitDto;

import java.util.Map;

public interface SettlementService {

    SettlementInitDTO initData(String productId, String buyNum) throws TrustException;

    Map<String,Object> dependency();

    SettlementSubmitDto submitOrder(SettlementOrderDTO settlementOrderDTO) throws TrustException;
}
