import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpuriousWakeupDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    // 条件：只有为 true 时，子线程才能执行
    private boolean canRun = false;

    public static void main(String[] args) {
        new SpuriousWakeupDemo().test();
    }

    public void test() {
        // 子线程
        new Thread(() -> {
            lock.lock();
            try {
                // =============== 危险！用 if 会被虚假唤醒击穿 ===============
                if (!canRun) {
                    System.out.println("子线程进入等待……期待被正确唤醒");
                    condition.await(); // wait
                }

                // 如果被虚假唤醒，这里会错误执行！
                System.out.println("⚠️ 子线程被唤醒了！此时 canRun = " + canRun);
                if (!canRun) {
                    System.out.println("❌ 灾难：这就是 虚假唤醒！条件不满足却跑下来了！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "子线程").start();

        // 主线程短暂等待，让子线程先进入 await
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }

        // 👇 重点：我这里 根本没有唤醒子线程！
        // 没有 signal() / notify()
        // 但某些情况下，子线程仍然会自己醒来 → 虚假唤醒
        lock.lock();
        try {
            condition.signal(); // 唤醒，但条件不满足！
        } finally {
            lock.unlock();
        }
    }
}