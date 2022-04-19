package org.trust.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trust.support.constant.ResultCodeConstant;
import org.trust.support.dto.ProductInfoDTO;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.export.ActivityExportService;
import org.trust.support.export.ProductExportService;
import org.trust.web.model.ActivityDescDTO;
import org.trust.web.model.ProductDescDTO;
import org.trust.web.model.ProductDetailDTO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = {"/activityDescData"},method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ActivityDescDTO activityDescData(String productId){
        try {
            Result<SeckillActivityDTO> result = activityExportService.queryActivity(productId);
            if (result==null|result.getData()==null){
                return null;
            }
            ActivityDescDTO activityDescDTO = new ActivityDescDTO();
            SeckillActivityDTO activityDTO = result.getData();
            BeanUtils.copyProperties(activityDTO,activityDescDTO);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            activityDescDTO.setActivityStartStr(sf.format(activityDTO.getActivityStart()));
            activityDescDTO.setActivityEndStr(sf.format(activityDTO.getActivityEnd()));
            Integer status = activityDTO.getStatus();
            String statusDesc="";
            switch (status){
                case 0:
                    statusDesc="未开始";
                    break;
                case 1:
                    statusDesc="进行中";
                    break;
                case 2:
                    statusDesc="已结束";
                    break;
                default:
                    break;
            }
            activityDescDTO.setStatusStr(statusDesc);
            return activityDescDTO;
        }catch (Exception e){
            log.error("活动信息查询异常！"+e);
        }
        return null;
    }

    @RequestMapping(value = {"/startActivity"},method = {RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String startActivity(String productId){
        try {
            Result<Integer> result= activityExportService.startActivity(productId);
            if (result==null||result.getData()==null){
                return "开始秒杀活动失败";
            }
            if (!org.springframework.util.StringUtils.endsWithIgnoreCase(result.getCode(),ResultCodeConstant.SYSTEM_EXCEPTION)){
                return "开始秒杀活动失败";
            }
            if (result.getData()==0){
                return "开始秒杀活动失败";
            }
            return "秒杀活动开启成功！";
        }catch (Exception exception){
            log.error("活动开始异常"+exception);
            return "开始秒杀活动失败";
        }
    }

    @RequestMapping(value = {"/endActivity"},method = {RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String endActivity(String productId){
        try {
            Result<Integer> result= activityExportService.endActivity(productId);
            if (result==null||result.getData()==null){
                return "结束秒杀活动失败";
            }
            if (!org.springframework.util.StringUtils.endsWithIgnoreCase(result.getCode(),ResultCodeConstant.SYSTEM_EXCEPTION)){
                return "结束秒杀活动失败";
            }
            if (result.getData()==0){
                return "结束秒杀活动失败";
            }
            return "秒杀活动结束成功！";
        }catch (Exception exception){
            log.error("秒杀活动结束异常："+exception);
            return "结束秒杀活动失败";
        }
    }

    @RequestMapping(value = {"/product"},method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ProductDetailDTO pruduct(String productId, HttpServletRequest request){
        Result<ProductInfoDTO> result = productExportService.queryProduct(productId);
        if (result==null||result.getData()==null){
            return null;
        }
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        ProductInfoDTO infoDTO = result.getData();
        productDetailDTO.setProductName(infoDTO.getProductName());
        productDetailDTO.setProductPrice(infoDTO.getProductPrice().toPlainString());
        productDetailDTO.setProductPictureUrl(infoDTO.getPictureUrl());
        productDetailDTO.setIsAvailable(0);
        productDetailDTO.setTag(infoDTO.getTag());
        return productDetailDTO;
    }

    //根据手机需求增加具体的controller
}
