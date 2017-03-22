package com.nnpcgroup.cosm.util;

import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.entity.tax.TaxOilKey;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Ayemi on 22/03/2017.
 */
@ApplicationScoped
public class CacheUtil {
    private CacheManager cacheManager;
    private Cache<TaxOilKey, TaxOilDetail> taxOilCache;
    private Cache<TaxOilKey, Double> royaltyCache;
    private Cache<TaxOilKey, Double> incomeCache;
    private Cache<TaxOilKey, Double> UAACache;
    private Cache<TaxOilKey, Double> amortizationCache;

    public CacheUtil() {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().build();
        cacheManager.init();

        taxOilCache = cacheManager
                .createCache("taxOilCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                TaxOilKey.class, TaxOilDetail.class,
                                ResourcePoolsBuilder.heap(12)));
        royaltyCache = cacheManager
                .createCache("royaltyCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                TaxOilKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        incomeCache = cacheManager
                .createCache("incomeCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                TaxOilKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        UAACache = cacheManager
                .createCache("UAACache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                TaxOilKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        amortizationCache = cacheManager
                .createCache("amortizationCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                TaxOilKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
    }

    public Cache<TaxOilKey, TaxOilDetail> getTaxOilCache() {
        return taxOilCache;
    }

    public Cache<TaxOilKey, Double> getRoyaltyCache() {
        return royaltyCache;
    }

    public Cache<TaxOilKey, Double> getIncomeCache() {
        return incomeCache;
    }

    public Cache<TaxOilKey, Double> getUAACache() {
        return UAACache;
    }

    public Cache<TaxOilKey, Double> getAmortizationCache() {
        return amortizationCache;
    }

    public void clearAll() {
        taxOilCache.clear();
        royaltyCache.clear();
        incomeCache.clear();
        UAACache.clear();
        amortizationCache.clear();
    }

    public void close() {
        cacheManager.close();
    }

}
