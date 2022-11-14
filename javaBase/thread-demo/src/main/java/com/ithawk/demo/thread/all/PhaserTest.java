package com.ithawk.demo.thread.all;

import java.util.concurrent.Phaser;

public class PhaserTest {
    public static void main(String[] args) {
        int parties = 3;
        int phases = 4;
        final Phaser phaser = new Phaser(parties) {
            @Override
            //每个阶段结束时
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== Phase : " + phase + "  end ======");
                return registeredParties == 0;
            }
        };
        for (int i = 0; i < parties; i++) {
            int threadId = i;
            Thread thread = new Thread(() -> {
                for (int phase = 0; phase < phases; phase++) {
                    if (phase == 0) {
                        System.out.println(String.format("第一阶段操作  Thread %s, phase %s", threadId, phase));
                    }
                    if (phase == 1) {
                        System.out.println(String.format("第二阶段操作  Thread %s, phase %s", threadId, phase));
                    }
                    if (phase == 2) {
                        System.out.println(String.format("第三阶段操作  Thread %s, phase %s", threadId, phase));
                    }
                    if (phase == 3) {
                        System.out.println(String.format("第四阶段操作  Thread %s, phase %s", threadId, phase));
                    }
                    /**
                     * arriveAndAwaitAdvance() 当前线程当前阶段执行完毕，等待其它线程完成当前阶段。
                     * 如果当前线程是该阶段最后一个未到达的，则该方法直接返回下一个阶段的序号（阶段序号从0开始），
                     * 同时其它线程的该方法也返回下一个阶段的序号。
                     **/
                    int nextPhaser = phaser.arriveAndAwaitAdvance();

                }
            });
            thread.start();
        }
    }
}