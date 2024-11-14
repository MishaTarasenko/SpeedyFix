package ukma.speedyfix.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Primary
@Component
public class CustomCacheManager extends ConcurrentMapCacheManager {

    private static final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    public CustomCacheManager() {
        super("todayWeather", "info");
    }

    @Override
    protected Cache createConcurrentMapCache(String name) {
        Cache cache = super.createConcurrentMapCache(name);
        cacheMap.put(name, cache);
        return cache;
    }
}
