//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import org.apache.curator.shaded.com.google.common.base.Throwables;
import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUtils {
    private static final Logger log = LoggerFactory.getLogger(ThreadUtils.class);

    public ThreadUtils() {
    }

    public static boolean checkInterrupted(Throwable e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
            return true;
        } else {
            return false;
        }
    }

    public static ExecutorService newSingleThreadExecutor(String processName) {
        return Executors.newSingleThreadExecutor(newThreadFactory(processName));
    }

    public static ExecutorService newFixedThreadPool(int qty, String processName) {
        return Executors.newFixedThreadPool(qty, newThreadFactory(processName));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(String processName) {
        return Executors.newSingleThreadScheduledExecutor(newThreadFactory(processName));
    }

    public static ScheduledExecutorService newFixedThreadScheduledPool(int qty, String processName) {
        return Executors.newScheduledThreadPool(qty, newThreadFactory(processName));
    }

    public static ThreadFactory newThreadFactory(String processName) {
        return newGenericThreadFactory("Curator-" + processName);
    }

    public static ThreadFactory newGenericThreadFactory(String processName) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                ThreadUtils.log.error("Unexpected exception in thread: " + t, e);
                Throwables.propagate(e);
            }
        };
        return (new ThreadFactoryBuilder()).setNameFormat(processName + "-%d").setDaemon(true).setUncaughtExceptionHandler(uncaughtExceptionHandler).build();
    }

    public static String getProcessName(Class<?> clazz) {
        return clazz.isAnonymousClass() ? getProcessName(clazz.getEnclosingClass()) : clazz.getSimpleName();
    }
}
