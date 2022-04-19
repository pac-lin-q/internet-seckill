package org.trust.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trust.support.constant.ResultCodeConstant;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.export.ActivityExportService;
import org.trust.support.export.ProductExportService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/mock")
public class MockController {

    @Autowired
    ProductExportService productExportService;

    @Autowired
    ActivityExportService activityExportService;

    @RequestMapping(value = {"/createActivity"},method = {RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createActivity(SeckillActivityDTO seckillActivityDTO){
        try {
            //创建测试数据，实际是页面传入
            seckillActivityDTO.setLimitNum(2);
            seckillActivityDTO.setActivityName("手机特价抢购");
            seckillActivityDTO.setStockNum(6);
            seckillActivityDTO.setActivityPrice(new BigDecimal("1199"));
            seckillActivityDTO.setActivityPictureUrl("/images/produc_seckill.jpg");
            Date now = new Date();
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH,2);
            seckillActivityDTO.setActivityStart(now);
            seckillActivityDTO.setActivityEnd(calendar.getTime());
            Result<Integer> result = activityExportService.createActivity(seckillActivityDTO);
            if (StringUtils.isEquals(result.getCode(), ResultCodeConstant.SUCCESS)){
                return "活动创建成功！";
            } else {
                return result.getMessage();
            }
        } catch (Exception exception){
            log.error("系统异常："+exception);
        }
        return "create fial";
    }
}
