package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * merge 原子合并计数
 *
 * key 无：存默认值 1
 * key 有：旧值 和 新值 按 lambda 合并求和
 */
public class MergeCountDemo {
    private static final ConcurrentHashMap<String, Integer> MERGE_MAP = new ConcurrentHashMap<>();

    public static void visit(String ip) {
        // 1. 不存在初始化为0
//        MERGE_MAP.putIfAbsent(ip, 0);
        // 2. 原子+1
        MERGE_MAP.merge(ip, 1, Integer::sum);
    }

    public static void main(String[] args) {
        visit("api/login");
        visit("api/login");
        visit("api/order");
        System.out.println(MERGE_MAP);
    }
}
