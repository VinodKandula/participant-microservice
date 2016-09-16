package com.amhzing.participant.configuration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.cache.expiry.CreatedExpiryPolicy.factoryOf;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            cacheManager.createCache("participantsCache",
                                     new MutableConfiguration<>().setExpiryPolicyFactory(factoryOf(new Duration(SECONDS, 10)))
                                                                 .setStoreByValue(false));
        };
    }
}
