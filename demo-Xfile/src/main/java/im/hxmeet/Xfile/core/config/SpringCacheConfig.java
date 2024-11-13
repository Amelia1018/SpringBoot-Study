package im.hxmeet.Xfile.core.config;

import cn.hutool.core.util.BooleanUtil;
import lombok.Value;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SpringCacheConfig {
    @Value("${xfile.dbCache.enable:true}")
    private Boolean dbCacheEnable;
    /**
     * 使用 TransactionAwareCacheManagerProxy 装饰 ConcurrentMapCacheManager，
     * 使其支持事务 （将 put、evict、clear 操作延迟到事务成功提交再执行.）
     */
    @Bean
    public CacheManager cacheManager() {
        return BooleanUtil.isFalse(dbCacheEnable) ? new NoOpCacheManager() : new TransactionAwareCacheManagerProxy(new ConcurrentMapCacheManager());
    }

}
