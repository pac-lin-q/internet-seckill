package org.trust.support.web.service;

import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;

public interface SeckillActivityService {

    Result<Integer> createActivity(SeckillActivityDTO seckillActivityDTO);
}
