//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.elasticjob.reg.redis;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public interface CuratorFramework extends Closeable {
    void start();

    void close();

//    CuratorFrameworkState getState();

    /** @deprecated */
    @Deprecated
    boolean isStarted();

//    CreateBuilder create();
//
//    DeleteBuilder delete();
//
//    ExistsBuilder checkExists();
//
//    GetDataBuilder getData();
//
//    SetDataBuilder setData();
//
//    GetChildrenBuilder getChildren();
//
//    GetACLBuilder getACL();
//
//    SetACLBuilder setACL();
//
//    ReconfigBuilder reconfig();
//
//    GetConfigBuilder getConfig();

    /** @deprecated */
//    CuratorTransaction inTransaction();
//
//    CuratorMultiTransaction transaction();
//
//    TransactionOp transactionOp();

    /** @deprecated */
    @Deprecated
    void sync(String var1, Object var2);

    void createContainers(String var1) throws Exception;

//    SyncBuilder sync();

    /** @deprecated */
//    RemoveWatchesBuilder watches();
//
//    WatchesBuilder watchers();
//
//    Listenable<ConnectionStateListener> getConnectionStateListenable();
//
//    Listenable<CuratorListener> getCuratorListenable();
//
//    Listenable<UnhandledErrorListener> getUnhandledErrorListenable();

    /** @deprecated */
    @Deprecated
    CuratorFramework nonNamespaceView();

    CuratorFramework usingNamespace(String var1);

    String getNamespace();

//    CuratorZookeeperClient getZookeeperClient();

    /** @deprecated */
//    @Deprecated
//    EnsurePath newNamespaceAwareEnsurePath(String var1);

    /** @deprecated */
//    @Deprecated
//    void clearWatcherReferences(Watcher var1);

    boolean blockUntilConnected(int var1, TimeUnit var2) throws InterruptedException;

    void blockUntilConnected() throws InterruptedException;

//    WatcherRemoveCuratorFramework newWatcherRemoveCuratorFramework();
//
//    ConnectionStateErrorPolicy getConnectionStateErrorPolicy();
//
//    QuorumVerifier getCurrentConfig();
//
//    SchemaSet getSchemaSet();

    default CompletableFuture<Void> postSafeNotify(Object monitorHolder) {
        return this.runSafe(() -> {
            synchronized(monitorHolder) {
                monitorHolder.notifyAll();
            }
        });
    }

    CompletableFuture<Void> runSafe(Runnable var1);

//    ConnectionStateErrorPolicy getConnectionStateErrorPolicy();
}
