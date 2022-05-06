package org.example.theproject.eviction.impl;

import org.example.theproject.enums.EvictionActionTypeEnum;
import org.example.theproject.eviction.EvictionPolicy;
import org.example.theproject.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FifoEvictionPolicyImpl<K> implements EvictionPolicy<K> {

    private final List<K> fifoList = new ArrayList<>();

    @Override
    public void updateEvictionDS(K key, EvictionActionTypeEnum actionTypeEnum) {
        switch (actionTypeEnum) {
            case INSERT:
                int position = 0;
                while (position<fifoList.size()) {
                    if (fifoList.get(position) == key) {
                        break;
                    }
                    position += 1;
                }
                if (position != fifoList.size()) {
                    fifoList.remove(position);
                }
                fifoList.add(key);
                break;
            case GET:
                break;
            case DELETE:
                int deletePosition = 0;
                while (deletePosition<fifoList.size()) {
                    if (fifoList.get(deletePosition) == key) {
                        System.out.println("Removing key -" + key);
                        break;
                    }
                    deletePosition += 1;
                }
                if (deletePosition != fifoList.size()) {
                    fifoList.remove(deletePosition);
                }
                break;
            case UPDATE:
                break;
            default:
                throw new InvalidRequestException("unknown Action performed");
        }
    }

    @Override
    public K getEvictedKey() {
        if (fifoList.isEmpty()) {
            throw new InvalidRequestException("No key to evict");
        }
        K key = fifoList.get(0);
        fifoList.remove(0);
        System.out.println("Removing key -" + key);
        return key;
    }
}
