package com.nnpcgroup.cosm.util;

import com.nnpcgroup.cosm.entity.tax.TaxOilDetail;
import com.nnpcgroup.cosm.report.schb1.CostOilAllocation;
import com.nnpcgroup.cosm.report.schb1.RoyaltyAllocation;
import com.nnpcgroup.cosm.report.schb1.TaxOilAllocation;
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
    private Cache<CacheKey, TaxOilDetail> taxOilCache;
    private Cache<CacheKey, Double> royaltyCache;
    private Cache<CacheKey, Double> incomeCache;
    private Cache<CacheKey, Double> UAACache;
    private Cache<CacheKey, Double> amortizationCache;
    private Cache<CacheKey, RoyaltyAllocation> royaltyAllocationCache;
    private Cache<CacheKey, CostOilAllocation> costOilAllocationCache;
    private Cache<CacheKey, TaxOilAllocation> taxOilAllocationCache;

    public CacheUtil() {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().build();
        cacheManager.init();

        taxOilCache = cacheManager
                .createCache("taxOilCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, TaxOilDetail.class,
                                ResourcePoolsBuilder.heap(12)));
        royaltyCache = cacheManager
                .createCache("royaltyCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        incomeCache = cacheManager
                .createCache("incomeCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        UAACache = cacheManager
                .createCache("UAACache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));
        amortizationCache = cacheManager
                .createCache("amortizationCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, Double.class,
                                ResourcePoolsBuilder.heap(100)));

        royaltyAllocationCache = cacheManager
                .createCache("royaltyAllocationCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, RoyaltyAllocation.class,
                                ResourcePoolsBuilder.heap(100)));

        costOilAllocationCache = cacheManager
                .createCache("costOilAllocationCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, CostOilAllocation.class,
                                ResourcePoolsBuilder.heap(100)));
        taxOilAllocationCache = cacheManager
                .createCache("taxOilAllocationCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                CacheKey.class, TaxOilAllocation.class,
                                ResourcePoolsBuilder.heap(100)));

    }

    public Cache<CacheKey, TaxOilDetail> getTaxOilCache() {
        return taxOilCache;
    }

    public Cache<CacheKey, Double> getRoyaltyCache() {
        return royaltyCache;
    }

    public Cache<CacheKey, Double> getIncomeCache() {
        return incomeCache;
    }

    public Cache<CacheKey, Double> getUAACache() {
        return UAACache;
    }

    public Cache<CacheKey, Double> getAmortizationCache() {
        return amortizationCache;
    }

    public Cache<CacheKey, RoyaltyAllocation> getRoyaltyAllocationCache() {
        return royaltyAllocationCache;
    }

    public Cache<CacheKey, CostOilAllocation> getCostOilAllocationCache() {
        return costOilAllocationCache;
    }

    public Cache<CacheKey, TaxOilAllocation> getTaxOilAllocationCache() {
        return taxOilAllocationCache;
    }

    public void clearAll() {
        taxOilCache.clear();
        royaltyCache.clear();
        incomeCache.clear();
        UAACache.clear();
        amortizationCache.clear();
        royaltyAllocationCache.clear();
        costOilAllocationCache.clear();
        taxOilAllocationCache.clear();
    }

    public void close() {
        cacheManager.close();
    }

}
