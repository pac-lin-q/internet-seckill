package org.trust.web.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 使用guava实现本地缓存
 * @param <K>
 * @param <V>
 */
@Data
public abstract class AbstractGuavaCache<K,V> {
    /**
     * guava cache
     */
    private LoadingCache<K, V> cache;

    /**
     * 缓存的key容量
     */
    private int maximumSize;

    /**
     * 缓存的失效时间
     */
    private int expireAfterDuration;

    /**
     * 缓存的失效时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 构建本地缓存
     */
    protected void init() {
        if (cache == null) {
            cache = CacheBuilder.newBuilder().maximumSize(maximumSize)
                    .expireAfterWrite(expireAfterDuration, timeUnit)
                    .build(new CacheLoader<K, V>() {
                        @Override
                        public V load(K key) {
                            return fetchData(key);
                        }
                    });
        }
    }

    protected V getValue(K key) throws ExecutionException {
        return cache.get(key);
    }

    protected abstract V fetchData(K key);
}
