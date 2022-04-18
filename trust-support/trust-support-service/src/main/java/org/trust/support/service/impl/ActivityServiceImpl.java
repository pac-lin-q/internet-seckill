package org.trust.support.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trust.support.dao.ActivityDao;
import org.trust.support.dao.ProductInfoDao;
import org.trust.support.entity.ActivityInfo;
import org.trust.support.exception.TrustException;
import org.trust.support.service.ActivityService;
import org.trust.support.tools.RedisTools;

import java.util.Date;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    RedisTools redisTools;

    /**
     * 创建活动
     * @param activityInfo
     * @return
     * @throws TrustException
     */
    @Override
    public int createActivity(ActivityInfo activityInfo) throws TrustException {
        ActivityInfo existActivit = activityDao.selectByCondition(activityInfo.getProductId(),null);
        if (existActivit!=null && (existActivit.getStatus()==1||existActivit.getStatus()==0)){
            throw new TrustException("活动已存在！");
        }
        activityInfo.setStatus(0);
        return activityDao.insert(activityInfo);
    }

    @Override
    public ActivityInfo queryActivityById(String productId) {
        return activityDao.selectByProductId(productId);
    }

    @Override
    public ActivityInfo queryActivityByCondition(String productId, Integer status) {
        return activityDao.selectByCondition(productId,status);
    }

    @Override
    public Integer startActivity(String productId) throws TrustException {
        //查询未开始的活动
        ActivityInfo activityInfo = activityDao.selectByCondition(productId,0);
        Date now = new Date();
        if (now.before(activityInfo.getActivityStart())){
            throw new TrustException("活动尚未开始");
        }
        if (now.after(activityInfo.getActivityEnd())){
            throw  new TrustException("活动已结束");
        }
        //更新活动为开始状态
        activityDao.updateStatus(activityInfo.getId(),1);
        //更新商品标识为秒杀
        productInfoDao.updateTag(productId,2);
        //将库存存入redis中
        redisTools.set("stroe_"+productId,String.valueOf(activityInfo.getStockNum()));
        return 1;
    }

    @Override
    public Integer endActivity(String productId) throws TrustException {
        //查询进行中的
        ActivityInfo activityInfo=activityDao.selectByCondition(productId,1);
        //更新活动状态为结束
        activityDao.updateStatus(activityInfo.getId(),2);
        //将商品标识改为普通
        productInfoDao.updateTag(productId,1);
        return 1;
    }

    @Override
    public Integer queryStore(String productId) {
        //查询进行中的活动库存
        ActivityInfo activityInfo=activityDao.selectByCondition(productId,1);
        return activityInfo.getStockNum();
    }
}
