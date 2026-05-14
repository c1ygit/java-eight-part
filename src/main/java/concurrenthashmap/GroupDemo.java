package concurrenthashmap;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分组聚合存储数据
 * <p>
 * 按用户 ID 归集订单、按部门归集工单
 */
public class GroupDemo {
    private static final ConcurrentHashMap<String, List<String>> GROUP_MAP = new ConcurrentHashMap<>();

    public static void addOrder(String userId, String orderNo) {
        // 不存在则初始化一个空列表，存在直接返回已有集合
        GROUP_MAP.computeIfAbsent(userId, k -> new java.util.concurrent.CopyOnWriteArrayList<>()).add(orderNo);
    }

    public static void main(String[] args) {
        addOrder("user1", "order1");
        addOrder("user1", "order2");
        addOrder("user2", "order3");
        System.out.println(GROUP_MAP);
    }
}
