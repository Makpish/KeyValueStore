package org.example.theproject;

import org.example.theproject.entity.KVStore;
import org.example.theproject.eviction.impl.FifoEvictionPolicyImpl;

public class Application {

    public static void main(String[] args) {
        KVStore kvStore = new KVStore(new FifoEvictionPolicyImpl(), 4);

        kvStore.insert("Hello", "World");
        printOutput("Hello", kvStore);

        kvStore.insert(123, "456");
        printOutput(123, kvStore);
        printOutput("Yash", kvStore);

        kvStore.insert("234", "hello");
        kvStore.update("Hello", "yash");
        printOutput("Hello", kvStore);

        kvStore.insert("100", 200);
        printOutput("100", kvStore);

        kvStore.insert("300", "400");
        printOutput("Hello", kvStore);

        printOutput("100", kvStore);

        kvStore.delete("100");

    }

    public static void printOutput(Object key, KVStore kvStore) {
        try {
            System.out.println(kvStore.get(key));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
