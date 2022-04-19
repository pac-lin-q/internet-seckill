package org.trust.web.limit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 方法限流公共组件
 */
@Component
@Slf4j
public class SecLimitComponent {
    private static final Cache<String, RateLimiter> RATE_LIMITER_MAP= CacheBuilder.newBuilder().maximumSize(50).build();

    private static final String INIT_LIMIT="1"; //限流key-进入结算页面
    private static final String SUBMIT_LIMIT="2"; // 限流key-提交订单

    @Value("${limiter.init.permitsPerSecond:2}")
    private double initPermitsPerSecond; //限流阀值-进入结算页面，每秒允许通过的请求数

    @Value("${limiter.submit.permitsPerSecond:1}")
    private double submitPermitsPerSecond; //限流阀值-提交订单，每秒允许通过的请求数

    /**
     * 进入结算页面限流
     * @return true 被限流，false 未被限流
     */
    public boolean isLimitedByInit(){
        return isLimited(INIT_LIMIT,initPermitsPerSecond);
    }

    /**
     * 提交订单限流
     * @return true 被限流，false 未被限流
     */
    public boolean isLimitedBySubmit(){
        return isLimited(SUBMIT_LIMIT,submitPermitsPerSecond);
    }

    /**
     * 公共限流方法
     * @param limiterKey
     * @param permitsPerSecond
     * @return
     */
    public boolean isLimited(String limiterKey,double permitsPerSecond){
        if (permitsPerSecond ==0){
            return true;
        }
        if (permitsPerSecond<0){
            return false;
        }
        try {
            RateLimiter rateLimiter = RATE_LIMITER_MAP.get(limiterKey,()->RateLimiter.create(permitsPerSecond));
            if (isLimitedAndUpdateRate(rateLimiter,permitsPerSecond)){
                return true;
            }
        } catch (Exception e){
            log.error(limiterKey+"限流异常："+e);
        }
        return false;
    }

    private boolean isLimitedAndUpdateRate(RateLimiter rateLimiter,double permitsPerSecond){
        //如果限流器设置阈值有变，则更新
        if (rateLimiter.getRate()!=permitsPerSecond){
            rateLimiter.setRate(permitsPerSecond);
        }
        //获取令牌，成功获取则 不被限流，为获取到，则是被限流了
        return !rateLimiter.tryAcquire();
    }
}
