package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;
import java.lang.String;

/**
 * 全局业务配置 / 开关、黑白名单
 */
public class SwitchDemo {
    private static final ConcurrentHashMap<String, Boolean> SWITCH_MAP = new ConcurrentHashMap<>();

    public static void setSwitchMap(String key, boolean flag) {
        SWITCH_MAP.put(key, flag);
    }

    // 获取开关
    public static boolean isOpen(String key) {
        // CHM 不存在返回 null，Boolean 解析为 false
        return SWITCH_MAP.get(key);
    }

    // 原子获取+不存在则设置默认值，并落地map
    public static boolean isOpenDefault(String key, boolean defaultValue) {
        return SWITCH_MAP.computeIfAbsent(key, k -> defaultValue);
    }

    public static void main(java.lang.String[] args) {
        setSwitchMap("featureA", true);
        setSwitchMap("featureB", false);

        System.out.println("Feature A is open: " + isOpen("featureA"));
        System.out.println("Feature B is open: " + isOpen("featureB"));
        System.out.println("Feature A is open (default false): " + isOpenDefault("featureA", false));
        System.out.println("Feature D is open (default true): " + isOpenDefault("featureC", true));
    }
}
