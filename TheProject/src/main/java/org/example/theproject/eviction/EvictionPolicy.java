package org.example.theproject.eviction;

import org.example.theproject.enums.EvictionActionTypeEnum;

public interface EvictionPolicy<K> {

    void updateEvictionDS(K key, EvictionActionTypeEnum actionTypeEnum);
    K getEvictedKey();
}
