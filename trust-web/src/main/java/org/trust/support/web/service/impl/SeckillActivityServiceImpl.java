package org.trust.support.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.export.ActivityExportService;
import org.trust.support.web.service.SeckillActivityService;

@Service
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Autowired
    ActivityExportService activityExportService;

    @Override
    public Result<Integer> createActivity(SeckillActivityDTO seckillActivityDTO) {
        return activityExportService.createActivity(seckillActivityDTO);
    }
}
