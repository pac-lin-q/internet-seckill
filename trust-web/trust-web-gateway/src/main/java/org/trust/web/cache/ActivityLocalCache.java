package org.trust.web.cache;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.trust.support.dto.Result;
import org.trust.support.dto.SeckillActivityDTO;
import org.trust.support.export.ActivityExportService;

import javax.annotation.PostConstruct;

@Slf4j
@Service(value = "activityLocalCache")
public class ActivityLocalCache extends  AbstractGuavaCache<String, SeckillActivityDTO> implements ILocalCache<String,SeckillActivityDTO>{

    @Value("${localcache.maximumSize:100}")
    private int maximumSize;

    @Value("${localcache.expire:10}")
    private int expireAfterDuration;

    @Autowired
    ActivityExportService activityExportService;

    @PostConstruct
    public void init(){
        setMaximumSize(maximumSize);
        setExpireAfterDuration(expireAfterDuration);
        super.init();
    }

    /**
     * 本地缓存不存在的时候，用来加载数据到本地缓存
     * @param key
     * @return
     */
    @Override
    protected SeckillActivityDTO fetchData(String key) {
        Result<SeckillActivityDTO> activityDTOResult = activityExportService.queryActivity(key);
        log.info("加载到本地的缓存："+ JSONObject.toJSONString(activityDTOResult));
        //防止发生缓存穿透，再拿不到的时候存放一个空对象
        if (activityDTOResult==null||activityDTOResult.getData()==null){
            return  new SeckillActivityDTO();
        }
        return activityDTOResult.getData();
    }

    @Override
    public SeckillActivityDTO get(String key) {
        try {
            return getValue(key);
        }catch (Exception e){
            log.error("activityLocalCache exception:"+ e);
            return null;
        }
    }
}
