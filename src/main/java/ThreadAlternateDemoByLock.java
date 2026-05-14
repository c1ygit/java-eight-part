import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子线程循环10次，接着主线程循环100，接着又回到子线程循环10次，接着再回到主线程又循环100，如此循环50次，请写出程序。
 */
public class ThreadAlternateDemoByLock {

    private final Lock lock = new ReentrantLock();
    private final Condition subCondition = lock.newCondition();
    private final Condition mainCondition = lock.newCondition();
    // 子线程执行标识：true=子线程执行
    private boolean subTurn = true;

    public static void main(String[] args) {
        new ThreadAlternateDemoByLock().init();
    }

    public void init() {
        new Thread(() -> {
            try {
                lock.lock();

                for (int round = 1; round <= 50; round++) {
                    // 不是子线程轮次，等待
                    while (!subTurn) {// 使用while防止虚假唤醒，虚假唤醒时再次判断subTurn，不符合则继续休眠
                        subCondition.await();
                    }

                    System.out.println("子线程第" + round + "轮次==");
                    for (int i = 1; i <= 10; i++) {
                        System.out.println("子线程" + i);
                    }

                    // 子线程执行一轮之后切换到主线程
                    subTurn = false;
                    mainCondition.signal();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "sub-thread").start();


        // 当前主线程
        try {
            lock.lock();

            for (int round = 1; round <= 50; round++) {
                // 不是主线程轮次，等待
                while (subTurn) {
                    mainCondition.await();
                }

                System.out.println("主线程第" + round + "轮次==");
                for (int i = 1; i <= 100; i++) {
                    System.out.println("主线程" + i);
                }
                // 主线程执行一轮之后切换到子线程
                subTurn = true;
                subCondition.signal();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }


}
