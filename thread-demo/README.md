# java 多线程
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
    