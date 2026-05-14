package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 常见场景1：本地缓存 ，替代hashmap+锁
 */
public class CHMCacheDemo {
    //  本地用户缓存
    private static final ConcurrentHashMap<Long, User> USER_CACHE = new ConcurrentHashMap<>();

    public static User getUser(Long userId) {
        return USER_CACHE.computeIfAbsent(userId, id -> loadUserFromDb(id));
    }

    // 模拟数据库查询
    private static User loadUserFromDb(Long id) {
        return new User(id, "User" + id, 20 + (int) (id % 10));
    }

    public static void main(String[] args) {
        System.out.println(getUser(1L));
        System.out.println(getUser(2L));
        System.out.println(getUser(1L));
    }

    static class User {
        Long id;
        String name;
        Integer age;

        public User(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "," + age;
        }
    }
}
