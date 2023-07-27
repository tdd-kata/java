package com.demo.springbootcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://spring.io/guides/gs/caching/">Caching Data with Spring</a>
 */
@Service
public class DemoService {

    // "cache::demo::data::SimpleKey []"
    @Cacheable(cacheNames = "cache::demo::data")
    public List<String> getData() {
        this.simulateSlowService();
        return List.of(
                "This 111 will be cached",
                "This 222 will be cached"
        );
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @CachePut(cacheNames = "cache::demo::data")
    public String getDataWithCachePut() {
        this.simulateSlowService();
        return "This will be executed everytime";
    }

    @CacheEvict(cacheNames = "cache::demo::data", allEntries = true)
    public void cacheEvict() {
    }

}
