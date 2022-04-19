package org.trust.web.cache;

public interface ILocalCache<K,V> {
    /**
     * 获取本地缓存数据
     * @param key
     * @return
     */
    V get(K key);
}
