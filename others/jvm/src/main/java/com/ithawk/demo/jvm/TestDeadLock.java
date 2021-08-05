package com.ithawk.demo.jvm;

/**
 * @className TestDeadLock
 * @description:  <p>
 *     jps
 *     获取：TestDeadLock 进程号
 *     jstack 进程号
 *
 *
 *     2021-08-05 17:38:13
 * Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.201-b09 mixed mode):
 *
 * "DestroyJavaVM" #22 prio=5 os_prio=0 tid=0x0000000002fd4800 nid=0xee4 waiting on condition [0x0000000000000000]
 *    java.lang.Thread.State: RUNNABLE
 *Found one Java-level deadlock:
 * =============================
 * "Thread-1":
 *   waiting to lock monitor 0x000000001cef5ec8 (object 0x000000076e2014a0, a java.lang.Object),
 *   which is held by "Thread-0"
 * "Thread-0":
 *   waiting to lock monitor 0x000000001cef8758 (object 0x000000076e2014b0, a java.lang.Object),
 *   which is held by "Thread-1"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-1":
 *         at com.ithawk.demo.jvm.TestDeadLock$Thread2.run(TestDeadLock.java:78)
 *         - waiting to lock <0x000000076e2014a0> (a java.lang.Object)
 *         - locked <0x000000076e2014b0> (a java.lang.Object)
 *         at java.lang.Thread.run(Thread.java:748)
 * "Thread-0":
 *         at com.ithawk.demo.jvm.TestDeadLock$Thread1.run(TestDeadLock.java:60)
 *         - waiting to lock <0x000000076e2014b0> (a java.lang.Object)
 *         - locked <0x000000076e2014a0> (a java.lang.Object)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.  发现死锁
 *
 *
 *
 *
 * </p>
 * @author IThawk
 * @date 2021/8/5 17:38
 */
public class TestDeadLock {
    private static Object obj1 = new Object();
    private static Object obj2 = new Object();

    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }

    private static class Thread1 implements Runnable {
        @Override
        public void run() {
            synchronized (obj1) {
                System.out.println("Thread1 拿到了 obj1 的锁！");
                try {
                   // 停顿2秒的意义在于，让Thread2线程拿到obj2的锁
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (obj2) {
                    System.out.println("Thread1 拿到了 obj2 的锁！");
                }
            }
        }
    }

    private static class Thread2 implements Runnable {
        @Override
        public void run() {
            synchronized (obj2) {
                System.out.println("Thread2 拿到了 obj2 的锁！");
                try {
                    // 停顿2秒的意义在于，让Thread1线程拿到obj1的锁
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {
                    System.out.println("Thread2 拿到了 obj1 的锁！");
                }
            }
        }
    }
}