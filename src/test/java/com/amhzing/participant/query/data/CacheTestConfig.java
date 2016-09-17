package com.amhzing.participant.query.data;

import com.amhzing.participant.query.data.jpa.repository.ParticipantQueryDslRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
@EnableCaching
public class CacheTestConfig {

    @MockBean
    ParticipantQueryDslRepository participantRepository;

    @Bean
    public QueryParticipant queryParticipant() {
        return new InMemQueryParticipant(participantRepository);
    }

    @Bean
    public SimpleCacheManager cacheManager(){
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        final List<Cache> caches = new ArrayList<>();
        caches.add(cacheBean().getObject());
        cacheManager.setCaches(caches );
        return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean cacheBean(){
        final ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("participantsCache");
        return cacheFactoryBean;
    }
}
