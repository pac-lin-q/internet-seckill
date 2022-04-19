package org.trust.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.export.ActivityExportService;
import org.trust.web.cache.ILocalCache;
import org.trust.web.model.ActivityDetailDTO;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityExportService activityExportService;

    @Resource(name = "activityLocalCache")
    ILocalCache iLocalCache;

    @CrossOrigin
    @RequestMapping(value = {"/queryStore"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Integer queryStore(String productId){
        try {
            Result<Integer> result = activityExportService.queryStore(productId);
            return result.getData();
        } catch ( Exception e){
            log.error("query activity store excetion："+e);
            return null;
        }
    }

    /**
     * 查询活动信息
     * @param productId
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = {"/subQuery"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ActivityDetailDTO subQuery(String productId){
        ActivityDetailDTO detailDTO = new ActivityDetailDTO();
        SeckillActivityDTO seckillActivityDTO = (SeckillActivityDTO) iLocalCache.get(productId);
        if (seckillActivityDTO == null){
            return null;
        }

        detailDTO.setProductPrice(seckillActivityDTO.getActivityPrice().toPlainString());
        detailDTO.setProductPictureUrl(seckillActivityDTO.getActivityPictureUrl());
        detailDTO.setProductName(seckillActivityDTO.getActivityName());
        Integer isAvailable=1;
        if (seckillActivityDTO.getStockNum()<=0){
            isAvailable=0;
        }
        Date now=new Date();
        if (now.before(seckillActivityDTO.getActivityStart())||now.after(seckillActivityDTO.getActivityEnd())){
            isAvailable=0;
        }
        detailDTO.setIsAvailable(isAvailable);
        return detailDTO;
    }
}
