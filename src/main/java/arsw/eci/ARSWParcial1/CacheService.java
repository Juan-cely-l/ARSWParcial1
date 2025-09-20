package arsw.eci.ARSWParcial1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CacheService {

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    private final long ttlSeconds;

    public CacheService(@Value("${cache.ttl.seconds:300}") long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public void put(String key, String value) {
        cache.put(key, new CacheEntry(value, Instant.now().getEpochSecond()));
    }

    public String get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) return null;
        long age = Instant.now().getEpochSecond() - entry.timestamp;
        if (age > ttlSeconds) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }

    public void invalidate(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    private static class CacheEntry {
        final String value;
        final long timestamp;
        CacheEntry(String value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}