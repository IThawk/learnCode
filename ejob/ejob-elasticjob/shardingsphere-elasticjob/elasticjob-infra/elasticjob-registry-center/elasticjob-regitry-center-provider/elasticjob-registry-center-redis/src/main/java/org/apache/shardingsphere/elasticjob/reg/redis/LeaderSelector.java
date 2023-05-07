//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.framework.state.ConnectionState;
//import org.apache.curator.shaded.com.google.common.annotations.VisibleForTesting;
//import org.apache.curator.shaded.com.google.common.collect.ImmutableList;
//import org.apache.curator.shaded.com.google.common.collect.Lists;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.elasticjob.reg.redis.exception.CancelLeadershipException;
import org.apache.shardingsphere.elasticjob.reg.redis.exception.CloseableExecutorService;
import org.apache.shardingsphere.elasticjob.reg.redis.exception.KeeperException;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaderSelector implements Closeable {
    private final Logger log;
    private final RedissonClient client;
    private final LeaderSelectorListener listener;
    private final CloseableExecutorService executorService;
    private final InterProcessMutex mutex;
    private final AtomicReference<State> state;
    private final AtomicBoolean autoRequeue;
    private final AtomicReference<Future<?>> ourTask;
    private volatile boolean hasLeadership;
    private volatile String id;
//    @VisibleForTesting
    volatile CountDownLatch debugLeadershipLatch;
    volatile CountDownLatch debugLeadershipWaitLatch;
    private boolean isQueued;
    private static final ThreadFactory defaultThreadFactory = ThreadUtils.newThreadFactory("LeaderSelector");
//    @VisibleForTesting
    volatile AtomicInteger failedMutexReleaseCount;

//    public LeaderSelector(RedissonClient client, String leaderPath, LeaderSelectorListener listener) {
//        this(client, leaderPath, (ExecutorService) new CloseableExecutorService(Executors.newSingleThreadExecutor(defaultThreadFactory), true), listener);
//    }

    /** @deprecated */
//    @Deprecated
//    public LeaderSelector(RedissonClient client, String leaderPath, ThreadFactory threadFactory, Executor executor, LeaderSelectorListener listener) {
//        this(client, leaderPath, (ExecutorService) new CloseableExecutorService(wrapExecutor(executor), true), listener);
//    }

//    public LeaderSelector(RedissonClient client,
//                          String leaderPath, ExecutorService executorService,
//                          LeaderSelectorListener listener) {
//        this(client, leaderPath,executorService, listener);
//    }

    public LeaderSelector(RedissonClient client, String leaderPath, CloseableExecutorService executorService, LeaderSelectorListener listener) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.state = new AtomicReference(LeaderSelector.State.LATENT);
        this.autoRequeue = new AtomicBoolean(false);
        this.ourTask = new AtomicReference((Object)null);
        this.id = "";
        this.debugLeadershipLatch = null;
        this.debugLeadershipWaitLatch = null;
        this.isQueued = false;
        this.failedMutexReleaseCount = null;
//        Preconditions.checkNotNull(client, "client cannot be null");
        PathUtils.validatePath(leaderPath);
//        Preconditions.checkNotNull(listener, "listener cannot be null");
        this.client = client;
        this.listener = new WrappedListener(this, listener);
        this.hasLeadership = false;
        this.executorService = executorService;
        this.mutex = new InterProcessMutex(client, leaderPath) {
            protected byte[] getLockNodeBytes() {
                return LeaderSelector.this.id.length() > 0 ? LeaderSelector.getIdBytes(LeaderSelector.this.id) : null;
            }
        };
    }


    static byte[] getIdBytes(String id) {
        try {
            return id.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new Error(var2);
        }
    }

    public void autoRequeue() {
        this.autoRequeue.set(true);
    }

    public void setId(String id) {
        Preconditions.checkNotNull(id, "id cannot be null");
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void start() {
        Preconditions.checkState(this.state.compareAndSet(LeaderSelector.State.LATENT, LeaderSelector.State.STARTED), "Cannot be started more than once");
        Preconditions.checkState(!this.executorService.isShutdown(), "Already started");
        Preconditions.checkState(!this.hasLeadership, "Already has leadership");
//        this.client.getConnectionStateListenable().addListener(this.listener);
        this.requeue();
    }

    public boolean requeue() {
        Preconditions.checkState(this.state.get() == LeaderSelector.State.STARTED, "close() has already been called");
        return this.internalRequeue();
    }

    private synchronized boolean internalRequeue() {
        if (!this.isQueued && this.state.get() == LeaderSelector.State.STARTED) {
            this.isQueued = true;
            Future<Void> task = this.executorService.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    try {
                        LeaderSelector.this.doWorkLoop();
                    } finally {
                        LeaderSelector.this.clearIsQueued();
                        if (LeaderSelector.this.autoRequeue.get()) {
                            LeaderSelector.this.internalRequeue();
                        }

                    }

                    return null;
                }
            });
            this.ourTask.set(task);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void close() {
        Preconditions.checkState(this.state.compareAndSet(LeaderSelector.State.STARTED, LeaderSelector.State.CLOSED), "Already closed or has not been started");
//        this.client.getConnectionStateListenable().removeListener(this.listener);
        this.executorService.close();
        this.ourTask.set((Future<?>) null);
    }

    public Collection<Participant> getParticipants() throws Exception {
        Collection<String> participantNodes = this.mutex.getParticipantNodes();
        return getParticipants(this.client, participantNodes);
    }

    static Collection<Participant> getParticipants(RedissonClient client, Collection<String> participantNodes) throws Exception {
        ImmutableList.Builder<Participant> builder = ImmutableList.builder();
        boolean isLeader = true;
        Iterator var4 = participantNodes.iterator();

        while(var4.hasNext()) {
            String path = (String)var4.next();
            Participant participant = participantForPath(client, path, isLeader);
            if (participant != null) {
                builder.add(participant);
                isLeader = false;
            }
        }

        return builder.build();
    }

    public Participant getLeader() throws Exception {
        Collection<String> participantNodes = this.mutex.getParticipantNodes();
        return getLeader(this.client, participantNodes);
    }

    static Participant getLeader(RedissonClient client, Collection<String> participantNodes) throws Exception {
        Participant result = null;
        if (participantNodes.size() > 0) {
            Iterator<String> iter = participantNodes.iterator();

            while(iter.hasNext()) {
                result = participantForPath(client, (String)iter.next(), true);
                if (result != null) {
                    break;
                }
            }
        }

        if (result == null) {
            result = new Participant();
        }

        return result;
    }

    public boolean hasLeadership() {
        return this.hasLeadership;
    }

    public synchronized void interruptLeadership() {
        Future<?> task = (Future)this.ourTask.get();
        if (task != null) {
            task.cancel(true);
        }

    }

    private static Participant participantForPath(RedissonClient client, String path, boolean markAsLeader) throws Exception {
        byte[] bytes = (byte[])client.getBucket(path).get().toString().getBytes();
        String thisId = new String(bytes, "UTF-8");
        return new Participant(thisId, markAsLeader);
    }

    @VisibleForTesting
    void doWork() throws Exception {
        this.hasLeadership = false;
        boolean var35 = false;

        try {
            label471: {
                var35 = true;
                this.mutex.acquire();
                this.hasLeadership = true;

                label463: {
                    try {
                        if (this.debugLeadershipLatch != null) {
                            this.debugLeadershipLatch.countDown();
                        }

                        if (this.debugLeadershipWaitLatch != null) {
                            this.debugLeadershipWaitLatch.await();
                        }

                        this.listener.takeLeadership(this.client);
                        break label463;
                    } catch (InterruptedException var49) {
                        Thread.currentThread().interrupt();
                        throw var49;
                    } catch (Throwable var50) {
                        ThreadUtils.checkInterrupted(var50);
                    } finally {
                        this.clearIsQueued();
                    }

                    var35 = false;
                    break label471;
                }

                var35 = false;
            }
        } catch (InterruptedException var52) {
            Thread.currentThread().interrupt();
            throw var52;
        } finally {
            if (var35) {
                if (this.hasLeadership) {
                    this.hasLeadership = false;
                    boolean wasInterrupted = Thread.interrupted();

                    try {
                        this.mutex.release();
                    } catch (Exception var45) {
                        if (this.failedMutexReleaseCount != null) {
                            this.failedMutexReleaseCount.incrementAndGet();
                        }

                        ThreadUtils.checkInterrupted(var45);
                        this.log.error("The leader threw an exception", var45);
                    } finally {
                        if (wasInterrupted) {
                            Thread.currentThread().interrupt();
                        }

                    }
                }

            }
        }

        if (this.hasLeadership) {
            this.hasLeadership = false;
            boolean wasInterrupted = Thread.interrupted();

            try {
                this.mutex.release();
            } catch (Exception var47) {
                if (this.failedMutexReleaseCount != null) {
                    this.failedMutexReleaseCount.incrementAndGet();
                }

                ThreadUtils.checkInterrupted(var47);
                this.log.error("The leader threw an exception", var47);
            } finally {
                if (wasInterrupted) {
                    Thread.currentThread().interrupt();
                }

            }
        }

    }

    private void doWorkLoop() throws Exception {
        KeeperException exception = null;

        try {
            this.doWork();
        } catch (KeeperException.ConnectionLossException var3) {
            exception = var3;
        } catch (KeeperException.SessionExpiredException var4) {
            exception = var4;
        } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
        }

        if (exception != null && !this.autoRequeue.get()) {
            throw (Exception)exception;
        }
    }

    private synchronized void clearIsQueued() {
        this.isQueued = false;
    }

    private static ExecutorService wrapExecutor(final Executor executor) {
        return new AbstractExecutorService() {
            private volatile boolean isShutdown = false;
            private volatile boolean isTerminated = false;

            public void shutdown() {
                this.isShutdown = true;
            }

            public List<Runnable> shutdownNow() {
                return Lists.newArrayList();
            }

            public boolean isShutdown() {
                return this.isShutdown;
            }

            public boolean isTerminated() {
                return this.isTerminated;
            }

            public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                throw new UnsupportedOperationException();
            }

            public void execute(Runnable command) {
                try {
                    executor.execute(command);
                } finally {
                    this.isShutdown = true;
                    this.isTerminated = true;
                }

            }
        };
    }

    private static class WrappedListener implements LeaderSelectorListener {
        private final LeaderSelector leaderSelector;
        private final LeaderSelectorListener listener;

        public WrappedListener(LeaderSelector leaderSelector, LeaderSelectorListener listener) {
            this.leaderSelector = leaderSelector;
            this.listener = listener;
        }

        public void takeLeadership(RedissonClient client) throws Exception {
            this.listener.takeLeadership(client);
        }

        public void stateChanged(RedissonClient client, ConnectionState newState) {
            try {
                this.listener.stateChanged(client, newState);
            } catch (CancelLeadershipException var4) {
                this.leaderSelector.interruptLeadership();
            }

        }

    }

    private static enum State {
        LATENT,
        STARTED,
        CLOSED;

        private State() {
        }
    }
}
