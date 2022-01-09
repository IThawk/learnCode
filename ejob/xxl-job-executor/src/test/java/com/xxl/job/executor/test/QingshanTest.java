package com.xxl.job.executor.test;

import java.util.Calendar;

public class QingshanTest {
    public static void main(String[] args) throws InterruptedException {
/*        for(int i=0;i<60;i++){
            int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
            System.out.println(nowSecond);
            Thread.sleep(1000);
        }*/
        for(int i=0;i<60;i++){
            int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
            System.out.print(nowSecond+"--");
            for (int k = 0; k < 2; k++) {
                System.out.print((nowSecond+60-k)%60);
                System.out.print(" ");
                System.out.print((nowSecond-k));
                System.out.print(" ");
            }
            Thread.sleep(1000);
            System.out.println();
        }
    }
}
