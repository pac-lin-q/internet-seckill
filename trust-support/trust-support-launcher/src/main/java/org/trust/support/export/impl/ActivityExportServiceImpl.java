package org.trust.support.export.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.constant.ResultCodeConstant;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.entity.ActivityInfo;
import org.trust.support.exception.TrustException;
import org.trust.support.export.ActivityExportService;
import org.trust.support.service.ActivityService;

@Service
@Slf4j
public class ActivityExportServiceImpl implements ActivityExportService {
    @Autowired
    ActivityService activityService;

    @Override
    public Result<Integer> queryStore(String productId) {
        try {
            Integer count= activityService.queryStore(productId);
            return new Result<>(count);
        }catch (Exception e){
            log.error("发生异常了："+e);
        }
        return new Result<>(ResultCodeConstant.SYSTEM_EXCEPTION,"系统异常",null);
    }

    @Override
    public Result<Integer> createActivity(SeckillActivityDTO activityDTO) {
        try {
            ActivityInfo activityInfo=new ActivityInfo();
            BeanUtils.copyProperties(activityDTO,activityInfo);
            int count = activityService.createActivity(activityInfo);
            return new Result<>(count);
        }catch (Exception e){
            log.error("发生异常了："+e);
        }
        return new Result<>(ResultCodeConstant.SYSTEM_EXCEPTION,"系统异常",null);
    }

    @Override
    public Result<SeckillActivityDTO> queryActivity(String productId) {
        ActivityInfo activityInfo=activityService.queryActivityById(productId);
        SeckillActivityDTO activityDTO=new SeckillActivityDTO();
        BeanUtils.copyProperties(activityInfo,activityDTO);
        return new Result<>(activityDTO);
    }

    @Override
    public Result<SeckillActivityDTO> queryActivityByCondition(String productId, Integer status) {
        ActivityInfo activityInfo=activityService.queryActivityByCondition(productId,status);
        if (activityInfo ==null){
            return new Result<>(null);
        }
        SeckillActivityDTO activityDTO=new SeckillActivityDTO();
        BeanUtils.copyProperties(activityInfo,activityDTO);
        return new Result<>(activityDTO);
    }

    @Override
    public Result<Integer> startActivity(String productId) {
        Integer count=0;
        try {
            count=activityService.startActivity(productId);
        }catch (Exception exception){
            log.error("发生异常了："+exception);
            return new Result<>(ResultCodeConstant.SYSTEM_EXCEPTION,"系统异常",null);
        }
        return new Result<>(count);
    }

    @Override
    public Result<Integer> endActivity(String productId) {
        Integer count=0;
        try {
            count=activityService.endActivity(productId);
        }catch (Exception exception){
            log.error("发生异常了："+exception);
            return new Result<>(ResultCodeConstant.SYSTEM_EXCEPTION,"系统异常",null);
        }
        return new Result<>(count);
    }
}
