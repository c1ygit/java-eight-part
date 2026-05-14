package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 统计每个 IP 接口访问次数，高并发不丢数据，且性能较好
 * <p>
 * map.put(ip, map.get(ip)+1) 非原子，并发会丢失计数；
 * compute 把读 - 改 - 写做成原子操作。
 */
public class IPCountDemo {
    private static final ConcurrentHashMap<String, Integer> IP_COUNT_MAP = new ConcurrentHashMap<>();

    public static void visit(String ip) {
        // 1. 不存在初始化为0
//        IP_COUNT_MAP.putIfAbsent(ip, 0);
        // 2. 原子+1
        IP_COUNT_MAP.compute(ip, (k, oldvalue) -> oldvalue == null ? 1 : oldvalue + 1);
    }

    public static void main(String[] args) {
        visit("192.168.1.1");
        visit("192.168.1.1");
        System.out.println(IP_COUNT_MAP);
    }
}
