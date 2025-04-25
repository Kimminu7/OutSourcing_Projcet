package org.example.outsourcing_project.common.cash;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CashConfig {
    //안되면 수정해야지 안될 수 가 있나?
    @Bean(name = "shopOpenStatusCacheManager")
    public CacheManager shopOpenStatusCacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)  // ✅ TTL 1분
                .maximumSize(1000);                     // ✅ 최대 캐시 수

        CaffeineCacheManager cacheManager = new CaffeineCacheManager("shopOpenStatus");
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
    @Bean(name = "shopStarCacheManager")
    public CacheManager shopStarCacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)  // ✅ TTL 1분
                .maximumSize(1000);                     // ✅ 최대 캐시 수

        CaffeineCacheManager cacheManager = new CaffeineCacheManager("shopStar");
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}
