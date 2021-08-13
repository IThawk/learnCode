# java 多线程
## 二. jvm 内存模型划分
    程序计数器(线程私有)：
    是当前线程锁执行字节码的行号治时期，每条线程都有一个独立的程序计数器，这类内存也称为“线程私有”的内存。正在执行java方法的话，计数器记录的是虚拟机字节码指令的地址(当前指令的地址)。如果是Natice方法，则为空。
    
    java 虚拟机栈
    也是线程私有的。
    每个方法在执行的时候也会创建一个栈帧，存储了局部变量，操作数，动态链接，方法返回地址。
    每个方法从调用到执行完毕，对应一个栈帧在虚拟机栈中的入栈和出栈。
    通常所说的栈，一般是指在虚拟机栈中的局部变量部分。
    局部变量所需内存在编译期间完成分配，
    如果线程请求的栈深度大于虚拟机所允许的深度，则StackOverflowError。
    如果虚拟机栈可以动态扩展，扩展到无法申请足够的内存，则OutOfMemoryError。
    本地方法栈（线程私有）
    和虚拟机栈类似，主要为虚拟机使用到的Native方法服务。也会抛出StackOverflowError 和OutOfMemoryError。
    
    Java堆（线程共享）
    被所有线程共享的一块内存区域，在虚拟机启动的时候创建，用于存放对象实例。
    对可以按照可扩展来实现（通过-Xmx 和-Xms 来控制）
    当队中没有内存可分配给实例，也无法再扩展时，则抛出OutOfMemoryError异常。
    方法区（线程共享）
    被所有方法线程共享的一块内存区域。
    用于存储已经被虚拟机加载的类信息，常量，静态变量等。
    这个区域的内存回收目标主要针对常量池的回收和堆类型的卸载。
## 一 · Thread(Runnable) 线程 
### 线程的状态：
* 1：new :线程初始化状态
    ```
  Thread thread = new Thread();
    ```
* 2:ready: 准备就绪状态：
    * 1：running 运行状态
* 3：timeWaiting: 时间等待状态
    ```
    thread.sleep(1000);
    ```
* 4: waiting：等待状态
    ```
    object.wait();
  
    object.notify();
    object.notifyAll();
    ```
* 5: Blocke: 阻塞状态
    现在没有抢占到资源，导致处于阻塞状态
* 6：dead 线程结束状态
    ```
    thread.interrupt();
    ``` 

## 二·synchronized关键字    
* 解决数据安全问题
* 线程之间进行通信
## 三·volatile 解决可见先问题  
在每个volatile写操作的前面插入一个StoreStore屏障。
·在每个volatile写操作的后面插入一个StoreLoad屏障。
·在每个volatile读操作的后面插入一个LoadLoad屏障。
·在每个volatile读操作的后面插入一个LoadStore屏障。
## 四·Lock
### 1：ReentrantLock 互斥可重入锁
```
        Lock lock = new ReentrantLock();
        lock.lock(); //获得一个锁
        lock.unlock();// 释放锁
```
### 2：ReentrantReadWriteLock 读写锁
```
ReentrantReadWriteLock wrl=new ReentrantReadWriteLock();
Lock read = wrl.readLock();
Lock write = wrl.writeLock();
```
## 五.多线程其他API
### 1：Condition
    
### 2：CountDownLatch 到达一定条件进行唤醒

### 3：CyclicBarrier

### 4：Semaphore 并发限制


### 六：引用

Java 的强引用、弱引用、软引用、虚引用
1、强引用（StrongReference）

        强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。如下：

Object o=new Object();   //  强引用
       当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。如果不使用时，要通过如下方式来弱化引用，如下：

o=null;     // 帮助垃圾收集器回收此对象
       显式地设置o为null，或超出对象的生命周期范围，则gc认为该对象不存在引用，这时就可以回收这个对象。具体什么时候收集这要取决于gc的算法。

举例：

public void test(){
    Object o=new Object();
    // 省略其他操作
}
       在一个方法的内部有一个强引用，这个引用保存在栈中，而真正的引用内容（Object）保存在堆中。当这个方法运行完成后就会退出方法栈，则引用内容的引用不存在，这个Object会被回收。

       但是如果这个o是全局的变量时，就需要在不用这个对象时赋值为null，因为强引用不会被垃圾回收。

       强引用在实际中有非常重要的用处，举个ArrayList的实现源代码：

复制代码
private transient Object[] elementData;
public void clear() {
        modCount++;
        // Let gc do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;
        size = 0;
}
复制代码
       在ArrayList类中定义了一个私有的变量elementData数组，在调用方法清空数组时可以看到为每个数组内容赋值为null。不同于elementData=null，强引用仍然存在，避免在后续调用 add()等方法添加元素时进行重新的内存分配。使用如clear()方法中释放内存的方法对数组中存放的引用类型特别适用，这样就可以及时释放内存。 

2、软引用（SoftReference）

      如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。软引用可用来实现内存敏感的高速缓存。      

String str=new String("abc");                                     // 强引用
SoftReference<String> softRef=new SoftReference<String>(str);     // 软引用
当内存不足时，等价于：    

If(JVM.内存不足()) {
   str = null;  // 转换为软引用
   System.gc(); // 垃圾回收器进行回收
}
  软引用在实际中有重要的应用，例如浏览器的后退按钮。按后退时，这个后退时显示的网页内容是重新进行请求还是从缓存中取出呢？这就要看具体的实现策略了。

（1）如果一个网页在浏览结束时就进行内容的回收，则按后退查看前面浏览过的页面时，需要重新构建

（2）如果将浏览过的网页存储到内存中会造成内存的大量浪费，甚至会造成内存溢出

这时候就可以使用软引用

复制代码
Browser prev = new Browser();               // 获取页面进行浏览
SoftReference sr = new SoftReference(prev); // 浏览完毕后置为软引用        
if(sr.get()!=null){ 
    rev = (Browser) sr.get();           // 还没有被回收器回收，直接获取
}else{
    prev = new Browser();               // 由于内存吃紧，所以对软引用的对象回收了
    sr = new SoftReference(prev);       // 重新构建
}
复制代码
      这样就很好的解决了实际的问题。

     软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收器回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。

3、弱引用（WeakReference）

      弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。 

String str=new String("abc");    
WeakReference<String> abcWeakRef = new WeakReference<String>(str);
str=null;
    当垃圾回收器进行扫描回收时等价于：    

str = null;
System.gc();
    如果这个对象是偶尔的使用，并且希望在使用时随时就能获取到，但又不想影响此对象的垃圾收集，那么你应该用 Weak Reference 来记住此对象。   

   下面的代码会让str再次变为一个强引用：    

String  abc = abcWeakRef.get();
    弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。

当你想引用一个对象，但是这个对象有自己的生命周期，你不想介入这个对象的生命周期，这时候你就是用弱引用。

   这个引用不会在对象的垃圾回收判断中产生任何附加的影响

复制代码
public class ReferenceTest {

    private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();

    public static void checkQueue() {
        Reference<? extends VeryBig> ref = null;
        while ((ref = rq.poll()) != null) {
            if (ref != null) {
                System.out.println("In queue: "    + ((VeryBigWeakReference) (ref)).id);
            }
        }
    }

    public static void main(String args[]) {
        int size = 3;
        LinkedList<WeakReference<VeryBig>> weakList = new LinkedList<WeakReference<VeryBig>>();
        for (int i = 0; i < size; i++) {
            weakList.add(new VeryBigWeakReference(new VeryBig("Weak " + i), rq));
            System.out.println("Just created weak: " + weakList.getLast());

        }

        System.gc(); 
        try { // 下面休息几分钟，让上面的垃圾回收线程运行完成
            Thread.currentThread().sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkQueue();
    }
}

class VeryBig {
    public String id;
    // 占用空间,让线程进行回收
    byte[] b = new byte[2 * 1024];

    public VeryBig(String id) {
        this.id = id;
    }

    protected void finalize() {
        System.out.println("Finalizing VeryBig " + id);
    }
}

class VeryBigWeakReference extends WeakReference<VeryBig> {
    public String id;

    public VeryBigWeakReference(VeryBig big, ReferenceQueue<VeryBig> rq) {
        super(big, rq);
        this.id = big.id;
    }

    protected void finalize() {
        System.out.println("Finalizing VeryBigWeakReference " + id);
    }
}
复制代码
最后的输出结果为：

复制代码
Just created weak: com.javabase.reference.VeryBigWeakReference@1641c0
Just created weak: com.javabase.reference.VeryBigWeakReference@136ab79
Just created weak: com.javabase.reference.VeryBigWeakReference@33c1aa
Finalizing VeryBig Weak 2
Finalizing VeryBig Weak 1
Finalizing VeryBig Weak 0
In queue: Weak 1
In queue: Weak 2
In queue: Weak 0
复制代码
4、虚引用（PhantomReference）

    “虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。

    虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列 （ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之 关联的引用队列中。

5、总结

    Java4种引用的级别由高到低依次为：

    强引用  >  软引用  >  弱引用  >  虚引用

    通过图来看一下他们之间在垃圾回收时的区别：

     

       当垃圾回收器回收时，某些对象会被回收，某些不会被回收。垃圾回收器会从根对象Object来标记存活的对象，然后将某些不可达的对象和一些引用的对象进行回收，如果对这方面不是很了解，可以参考如下的文章：

      通过表格来说明一下，如下：

      
    