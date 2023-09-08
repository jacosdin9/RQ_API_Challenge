package com.example.rqchallenge.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    private static final String ALL_EMPLOYEE_CACHE = "allEmployeeCache";

    @Bean
    public CacheManager ehCacheManagerFactory() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<ConcurrentMapCache> caches =
                List.of(new ConcurrentMapCache(ALL_EMPLOYEE_CACHE));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
