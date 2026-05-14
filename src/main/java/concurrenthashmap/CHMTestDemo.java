package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

public class CHMTestDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.compute("key1", (k, v) -> v == null ? "value1" : v + "value1");
        map.compute("key1", (k, v) -> v == null ? "value1" : v + "value1");
        System.out.println(map);
    }
}
