package com.cosm.common.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Ayemi on 20/02/2018.
 */
//@ApplicationScoped
public class CacheUtil<T, K> {
    private CacheManager cacheManager;
    private Cache<T, K> cache;    
    private Class<T> keyClass;
    private Class<K> dataClass;
    


    public CacheUtil(String cacheName, int heapSize) {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().build();
        cacheManager.init();

        cache = cacheManager
                .createCache(cacheName, CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                        		keyClass, dataClass,
                                ResourcePoolsBuilder.heap(heapSize)));     
    }

    public Cache<T, K> cache() {
        return cache;
    }

    public void clear() {
        cache.clear();        
    }

    public void close() {
        cacheManager.close();
    }

}
