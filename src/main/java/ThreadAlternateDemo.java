/**
 * 子线程循环10次，接着主线程循环100，接着又回到子线程循环10次，接着再回到主线程又循环100，如此循环50次，请写出程序。
 */
public class ThreadAlternateDemo {

    // 控制交替的锁
    private final Object lock = new Object();
    // 是否应该子线程执行
    private boolean subTurn = true;

    public static void main(String[] args) {
        new ThreadAlternateDemo().init();

        /*try {
            // 创建线程池
            ExecutorService pool = Executors.newFixedThreadPool(2);

            pool.submit(() -> System.out.println("submit runnable task"));

            Future<String> future = pool.submit(new Callable<String>() {
                @Override
                public String call() {
                    System.out.println("hello executorpool ");
                    return "chenyun";
                }
            });
            // 获取结果
            System.out.println("结果：" + future.get());

            pool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void init() {
        new Thread(() -> {
            try {
                for (int round = 1; round <= 50; round++) {

                    synchronized (lock) {
                        while (!subTurn) {
                            lock.wait();
                        }

                        System.out.println("===== 子线程第 " + round + " 轮开始 =====");
                        // 子线程循环10次
                        for (int j = 1; j <= 10; j++) {
                            System.out.println("子线程运行：" + j);
                        }

                        // 子线程结束后切换主线程
                        subTurn = false;
                        lock.notify();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "子线程").start();

        // 当前主线程
        try {
            for (int round = 1; round <= 50; round++) {
                synchronized (lock) {
                    while (subTurn) {
                        lock.wait();
                    }

                    System.out.println("===== 主线程第 " + round + " 轮开始 =====");
                    // 主线程循环100次
                    for (int j = 1; j <= 100; j++) {
                        System.out.println("主线程运行：" + j);
                    }

                    // 主线程结束后切换子线程
                    subTurn = true;
                    lock.notify();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}