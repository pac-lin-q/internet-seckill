package org.trust.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trust.support.dto.SettlementOrderDTO;
import org.trust.web.limit.SecLimitComponent;
import org.trust.web.model.SettlementInitDTO;
import org.trust.web.model.SettlementSubmitDto;
import org.trust.web.service.SettlementService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    SettlementService settlementService;

    @Autowired
    SecLimitComponent secLimitComponent;

    @RequestMapping(value = {"/initSettlement"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public SettlementInitDTO initSettlement(String pruductId, String buyNum, HttpServletRequest request){
        log.info("结算页面初始化，入参：productId："+pruductId+",buyNum:"+buyNum);
        //判断是否被限流
        if (secLimitComponent.isLimitedByInit()){
            return null;
        }
        SettlementInitDTO initDTO=null;
        try {
            initDTO = settlementService.initData(pruductId,buyNum);
        }catch (Exception e){
            log.error("结算页面接口异常："+e);
        }
        return initDTO;
    }

    @RequestMapping(value = {"/submitSettlement"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public SettlementSubmitDto submitSettlement(SettlementOrderDTO settlementOrderDTO){
        //判断是否限流
        if (secLimitComponent.isLimitedByInit()){
            return null;
        }
        //mock数据
        settlementOrderDTO.setBuyNum(2);
        settlementOrderDTO.setAddress("上海市疫情封控区");
        settlementOrderDTO.setPayType(1);

        SettlementSubmitDto submitDto = new SettlementSubmitDto();
        submitDto.setCode("000000");
        try {
            submitDto = settlementService.submitOrder(settlementOrderDTO);
        }catch (Exception e){
            submitDto.setCode("000001");
            submitDto.setMessage("系统出小差了，请稍后再试！");
            log.error("系统出小差了:"+e);
        }
        return submitDto;
    }
}
