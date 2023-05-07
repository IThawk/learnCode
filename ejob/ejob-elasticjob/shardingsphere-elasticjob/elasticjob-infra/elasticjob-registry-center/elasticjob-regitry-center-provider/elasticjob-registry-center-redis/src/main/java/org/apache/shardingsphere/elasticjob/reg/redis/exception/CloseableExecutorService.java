//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis.exception;

import java.io.Closeable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.curator.shaded.com.google.common.annotations.VisibleForTesting;
import org.apache.curator.shaded.com.google.common.base.Preconditions;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseableExecutorService implements Closeable {
    private final Logger log;
    private final Set<Future<?>> futures;
    private final ExecutorService executorService;
    private final boolean shutdownOnClose;
    protected final AtomicBoolean isOpen;

    public CloseableExecutorService(ExecutorService executorService) {
        this(executorService, false);
    }

    public CloseableExecutorService(ExecutorService executorService, boolean shutdownOnClose) {
        this.log = LoggerFactory.getLogger(CloseableExecutorService.class);
        this.futures = Sets.newSetFromMap(Maps.newConcurrentMap());
        this.isOpen = new AtomicBoolean(true);
        this.executorService = (ExecutorService)Preconditions.checkNotNull(executorService, "executorService cannot be null");
        this.shutdownOnClose = shutdownOnClose;
    }

    public boolean isShutdown() {
        return !this.isOpen.get();
    }

    @VisibleForTesting
    int size() {
        return this.futures.size();
    }

    public void close() {
        this.isOpen.set(false);
        Iterator<Future<?>> iterator = this.futures.iterator();

        while(iterator.hasNext()) {
            Future<?> future = (Future)iterator.next();
            iterator.remove();
            if (!future.isDone() && !future.isCancelled() && !future.cancel(true)) {
                this.log.warn("Could not cancel " + future);
            }
        }

        if (this.shutdownOnClose) {
            this.executorService.shutdownNow();
        }

    }

    public <V> Future<V> submit(Callable<V> task) {
        Preconditions.checkState(this.isOpen.get(), "CloseableExecutorService is closed");
        InternalFutureTask<V> futureTask = new InternalFutureTask(new FutureTask(task));
        this.executorService.execute(futureTask);
        return futureTask;
    }

    public Future<?> submit(Runnable task) {
        Preconditions.checkState(this.isOpen.get(), "CloseableExecutorService is closed");
        InternalFutureTask<Void> futureTask = new InternalFutureTask(new FutureTask(task, (Object)null));
        this.executorService.execute(futureTask);
        return futureTask;
    }

    protected class InternalFutureTask<T> extends FutureTask<T> {
        private final RunnableFuture<T> task;

        InternalFutureTask(RunnableFuture<T> task) {
            super(task, null);
            this.task = task;
            CloseableExecutorService.this.futures.add(task);
        }

        protected void done() {
            CloseableExecutorService.this.futures.remove(this.task);
        }
    }

    protected class InternalScheduledFutureTask implements Future<Void> {
        private final ScheduledFuture<?> scheduledFuture;

        public InternalScheduledFutureTask(ScheduledFuture<?> scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
            CloseableExecutorService.this.futures.add(scheduledFuture);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            CloseableExecutorService.this.futures.remove(this.scheduledFuture);
            return this.scheduledFuture.cancel(mayInterruptIfRunning);
        }

        public boolean isCancelled() {
            return this.scheduledFuture.isCancelled();
        }

        public boolean isDone() {
            return this.scheduledFuture.isDone();
        }

        public Void get() throws InterruptedException, ExecutionException {
            return null;
        }

        public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }
}
