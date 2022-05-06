package org.example.theproject.entity;

import org.example.theproject.enums.EvictionActionTypeEnum;
import org.example.theproject.eviction.EvictionPolicy;
import org.example.theproject.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KVStore<K,V> {

    private final Map<K, V> kvstore;
    private final EvictionPolicy<K> evictionPolicy;
    private final Integer size;

    public KVStore(EvictionPolicy evictionPolicy, Integer size) {
        this.size = size;
        this.kvstore = new ConcurrentHashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public void insert(K key, V value) {
        if (kvstore.containsKey(key)) {
            throw new NotFoundException("Key already set in the store");
        }
        if (kvstore.size() == size) {
            K keyToEvict = evictionPolicy.getEvictedKey();
            kvstore.remove(keyToEvict);
        }
        evictionPolicy.updateEvictionDS(key, EvictionActionTypeEnum.INSERT);
        kvstore.put(key, value);
    }

    public V get(K key) {
        if (!kvstore.containsKey(key)) {
            throw new NotFoundException("Key not found");
        }
        evictionPolicy.updateEvictionDS(key, EvictionActionTypeEnum.GET);
        return kvstore.get(key);
    }

    public void delete(K key) {
        if (!kvstore.containsKey(key)) {
            throw new NotFoundException("Key not found");
        }
        evictionPolicy.updateEvictionDS(key, EvictionActionTypeEnum.DELETE);
        kvstore.remove(key);
    }

    public void update(K key, V value) {
        if (!kvstore.containsKey(key)) {
            throw new NotFoundException("Key not found");
        }
        evictionPolicy.updateEvictionDS(key, EvictionActionTypeEnum.UPDATE);
        kvstore.put(key, value);
    }
}
