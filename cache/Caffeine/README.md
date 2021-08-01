GuavaCache和Caffeine差异 剔除算法方面，GuavaCache采用的是「LRU」算法，而Caffeine采用的是「Window TinyLFU」算法，这是两者之间最大，也是根本的区别。

立即失效方面，Guava会把立即失效 (例如：expireAfterAccess(0) and expireAfterWrite(0))
转成设置最大Size为0。这就会导致剔除提醒的原因是SIZE而不是EXPIRED。Caffiene能正确识别这种剔除原因。

取代提醒方面，Guava只要数据被替换，不管什么原因，都会触发剔除监听器。而Caffiene在取代值和先前值的引用完全一样时不会触发监听器。

异步化方方面，Caffiene的很多工作都是交给线程池去做的（默认：ForkJoinPool.commonPool()），例如：剔除监听器，刷新机制，维护工作等。

内存占用对比 Caffeine可以根据使用情况延迟初始化，或者动态调整它内部数据结构。这样能减少对内存的占用。
缓存的驱逐策略是为了预测哪些数据在短期内最可能被再次用到，从而提升缓存的命中率。由于简洁的实现、高效的运行时表现以及在常规的使用场景下有不错的命中率，LRU（Least Recently Used）策略或许是最流行的驱逐策略,，它在保持算法简单的前提下，效果还不错。但LRU对未来的预测有明显的局限性，它会认为「最后到来的数据是最可能被再次访问」的，从而给予它最高的优先级。
LRU P.K. W-TinyLFU
缓存的驱逐策略是为了预测哪些数据在短期内最可能被再次用到，从而提升缓存的命中率。由于简洁的实现、高效的运行时表现以及在常规的使用场景下有不错的命中率，LRU（Least Recently Used）策略或许是最流行的驱逐策略,，它在保持算法简单的前提下，效果还不错。但LRU对未来的预测有明显的局限性，它会认为「最后到来的数据是最可能被再次访问」的，从而给予它最高的优先级。

现代缓存扩展了对历史数据的使用，结合就近程度（recency）和访问频次（frequency）来更好的预测数据。其中一种保留历史信息的方式是使用「popularity sketch」（一种压缩、概率性的数据结构）来从一大堆访问事件中定位频繁的访问者。可以参考「CountMin Sketch」算法，它由计数矩阵和多个哈希方法实现。发生一次读取时，矩阵中每行对应的计数器增加计数，估算频率时，取数据对应是所有行中计数的最小值。这个方法让我们从空间、效率、以及适配矩阵的长宽引起的哈希碰撞的错误率上做权衡：

Window TinyLFU（W-TinyLFU）算法将Sketch作为过滤器，当新来的数据比要驱逐的数据高频时，这个数据才会被缓存接纳（admission）。这个许可窗口给予每个数据项积累热度的机会，而「不是立即过滤掉」。这避免了持续的未命中，特别是在突然流量暴涨的的场景中，一些短暂的重复流量就不会被长期保留。为了刷新历史数据，一个时间衰减进程被周期性或增量的执行，给所有计数器减半：

对于长期保留的数据，W-TinyLFU使用了分段LRU（Segmented LRU，缩写SLRU）策略。起初，一个数据项存储被存储在试用段（probationary segment）中，在后续被访问到时，它会被提升到保护段（protected segment）中（保护段占总容量的80%）。保护段满后，有的数据会被淘汰回试用段，这也可能级联的触发试用段的淘汰。这套机制确保了访问间隔小的热数据被保存下来，而被重复访问少的冷数据则被回收：

Guava迁移
那么，如果我的项目之前用的是GuavaCache，如何以尽可能低的成本迁移到Caffeine上来呢？
嘿嘿，Caffeine已经想到了这一点，它提供了一个适配器，让你用Guava的接口操作它的缓存。代码片段如下所示：

```
// Guava's LoadingCache interface
LoadingCache<Key, Graph> graphs = CaffeinatedGuava.build(
Caffeine.newBuilder().maximumSize(10_000),
new CacheLoader<Key, Graph>() { // Guava's CacheLoader
@Override public Graph load(Key key) throws Exception {
return createExpensiveGraph(key);
}
});
```

