package assignments;

// Q 4 (a)
//LFU caching evicts the least frequently used items from the cache when it reaches its capacity limit, hence making space for newer ones
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LFUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> keyFrequency;
    private final Map<Integer, Set<K>> frequencyKeys;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        keyFrequency = new HashMap<>();
        frequencyKeys = new HashMap<>();
        minFrequency = 0;
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        int frequency = keyFrequency.get(key);
        keyFrequency.put(key, frequency + 1);
        frequencyKeys.get(frequency).remove(key);
        if (frequency == minFrequency && frequencyKeys.get(frequency).isEmpty()) {
            minFrequency++;
        }
        frequencyKeys.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>()).add(key);
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }
        if (cache.containsKey(key)) {
            cache.put(key, value);
            get(key);
            return;
        }
        if (cache.size() >= capacity) {
            K evictKey = frequencyKeys.get(minFrequency).iterator().next();
            frequencyKeys.get(minFrequency).remove(evictKey);
            cache.remove(evictKey);
            keyFrequency.remove(evictKey);
        }
        cache.put(key, value);
        keyFrequency.put(key, 1);
        frequencyKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }
}
