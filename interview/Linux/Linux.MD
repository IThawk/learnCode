## uptime查看系统负载

```
[root@centos7-1 ~]# uptime
16:01:25 up 15 min, 1 user, load average: 0.00, 0.02, 0.05
[root@centos7-1 ~]#
```
结果解析：
```
16:01:25 // 当前时间
up 15 min // 系统运行时间
1 user // 正在登录用户数
load average: 0.00, 0.02, 0.05 //三个数字呢，依次则是过去 1 分钟、5 分钟、15 分钟的平均负载


如果 1 分钟、5 分钟、15 分钟的三个值基本相同，或者相差不大，那就说明系统负载很平稳。
如果 1 分钟的值远小于 15 分钟的值，就说明系统最近 1 分钟的负载在减少，而过去 15 分钟内却有很大的负载。
如果 1 分钟的值远大于 15 分钟的值，就说明最近 1 分钟的负载在增加，这种 增加有可能只是临时性的，也有可能
还会持续增加下去，所以就需要持续观察。一旦 1 分钟的平均负载接近或超过了 CPU 的个数，就意味着系统正在
发生过载的问题，这时就 得分析调查是哪里导致的问题，并要想办法优化了。
```

平均负载最理想的情况是等于 CPU 个数。所以在评判平均负载时，首先你要知 道系统有几个 CPU，这可以通过
top 命令或者从文件 /proc/cpuinfo 中读取

## 查看CPU 核心数

```
grep 'model name' /proc/cpuinfo | wc -l

[root@localhost ~]# grep 'model name' /proc/cpuinfo | wc -l
2

```

当平均负载比 CPU 个数还大的时候，系统已经出现 了过载。在观察负载数据时，我们有三个时间点的数据，因此
都要看。三个不同时间间隔的平均值，其实给我们提供了，分析系统负载趋势的 数据来源，让我们能更全面、更立体地理解目前的负载状况。
举个例子
假设我们在一个单 CPU 系统上看到平均负载为 1.88，0.70，6.76， 那么说明在过去 1 分钟内，系统有 188% 的超载，而在 15 分钟内，有 676% 的超载，从 整体趋势来看，系统的负载在降低当平均负载高于 CPU 数量 70% 的时候，你就应该分析排查负载高的问题了（70% 这个数字并不是绝对的，最推荐的方法）。

## 平均负载与CPU使用率

平均负载是指单位时间内，处于可运行状态和不可 中断状态的进程数。所
以，它不仅包括了正在使用 CPU 的进程，还包括等待 CPU 和等待 I/O 的进程。
而CPU使用率，是单位时间内CPU繁忙情况的统计，跟平均负载并不一定完全对应。比如

* CPU 密集型进程，使用大量 CPU 会导致平均负载升高，此时这两者是一致的；
* I/O 密集型进程，等待 I/O 也会导致平均负载升高，但 CPU 使用率不一定很高；
* 大量等待 CPU 的进程调度也会导致平均负载升高，此时的 CPU 使用率也会比较高。





一、stress的安装

1、yum install -y epel-release

2、yum install stress -y



2.sysstat 工具介绍
包含了常用的 Linux 性能工具，用来监控和分析系统的性能
接下来会用到 mpstat 和 pidstat 两个命令
3.mpstat
常用的多核 CPU 性能分析工具
实时查看每个 CPU 的性能指标以及所有 CPU 的平均指标
4.pidstat
常用的进程性能分析工具
实时查看进程的 CPU、内存、I/O 以及上下文切换等性能指标

### sysstat 命令安装

http://sebastien.godard.pagesperso-orange.fr/download.html

1. 1.删除原有老版本sysstat
   [root@node-x]# rpm -e --nodeps sysstat
   2.解压sysstat源码
   [root@node-x]# xz -d sysstat-11.5.5.tar.xz
   [root@node-x]# tar xf sysstat-11.5.5.tar
   3.安装前环境监测
   [root@node-x]# cd sysstat-11.5.5
   [root@node-x sysstat-11.5.5]# ./configure环境未具备，程序返回结果会报错,这里正常
   4.编译安装sysstat源码
   [root@node-x sysstat-11.5.5]# sa_lib_dir=/usr/lib/sa    \
   sa_dir=/var/log/sa        \
   conf_dir=/etc/sysconfig   \
   ./configure --prefix=/usr \
   --disable-file-attr && make && make install
2. 
3. 5.设置sysstat全局变量及开机启动
   ln -s /安装路径/sysstat-11.5.5/sysstat  /usr/local/bin/
   ln -s /安装路径/sysstat-11.5.5/sysstat  /etc/init.d/sysstat
   chkconfig --add sysstat
   chkconfig --list | grep sysstat
   6.手动启动sysstat
   [root@node-x sysstat-11.5.5]# /etc/rc.d/init.d/sysstat start
   Calling the system activity data collector (sadc)... 
   ————————————————
   版权声明：本文为CSDN博主「wen0220」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
   原文链接：https://blog.csdn.net/qq_34485930/article/details/80864016



如果是CPU密集型应用，则线程池大小设置为N+1

如果是IO密集型应用，则线程池大小设置为2N+1（因为io读数据或者缓存的时候，线程等待，此时如果多开线程，能有效提高cpu利用率）

### 场景一 : 模拟CPU 密集型进程

第一个终端:在第一个终端运行 stress 命令，模拟一个 CPU 使用率 100% 的场景

```
[root@centos7-2 ~]# stress --cpu 1 --timeout 600
stress: info: [6248] dispatching hogs: 1 cpu, 0 io, 0 vm, 0 hdd
```


第二个终端:运行 uptime 查看系统平均负载情况，-d 参数表示高亮显示变化的区域

```
[root@centos7-2 ~]# watch -d uptime
..., load average: 1.00, 0.75, 0.39
```


1 分钟的平均负载会慢慢增加到 1.00
第三个终端:运行 mpstat 查看 CPU 使用率的变化情况
仅有一个 CPU 的使用率接近 100%，但它的 iowait 只有 0,这说明，平均负载的升高正是由于 CPU 使用率为 100%

```
#-P ALL 表示监控所有 CPU，后面数字 5 表示间隔 5 秒后输出一组数据
[root@centos7-2 ~]# mpstat -P ALL 5
```

就要排查是哪个进程导致 CPU 的使用率这么高的
我们可以使用 pidstat 来查询

```
# pidstat [ 选项 ] [ <时间间隔> ] [ <次数> ]
# -u：默认的参数，显示各个进程的cpu使用统计
# -r：显示各个进程的内存使用统计
# -d：显示各个进程的IO使用情况
# -p：指定进程号
# -w：显示每个进程的上下文切换情况
# -t：显示选择任务的线程的统计信息外的额外信息
[root@localhost ~]# pidstat -u 5 1
Linux 3.10.0-1160.24.1.el7.x86_64 (localhost.localdomain) 	01/12/2022 	_x86_64_	(2 CPU)

10:23:39 AM   UID       PID    %usr %system  %guest    %CPU   CPU  Command
10:23:45 AM     0      2008   99.11    1.06    0.00  100.00     1  stress

Average:      UID       PID    %usr %system  %guest    %CPU   CPU  Command
Average:        0      2008   99.11    1.06    0.00  100.00     -  stress
[root@localhost ~]# 



从这里可以明显看到，stress 进程的 CPU 使用率为 99.80
```

场景二：模拟IO密集型的进程
第一个终端

运行 stress 命令，但这次模拟 I/O 压力，即不停地执行 sync()

```
[root@localhost stress]# stress -i 1 --timeout 600
stress: info: [2036] dispatching hogs: 0 cpu, 1 io, 0 vm, 0 hdd
```

第二个终端
运行 uptime 查看系统平均负载情况，-d 参数表示高亮显示变化的区域

```
# -d 参数表示高亮显示变化的区域
[root@centos7-2 ~]# watch -d uptime
11:17:08 up 33 min, 3 users, load average: 1.11, 0.91, 0.61
```

第三个终端
运行 mpstat 查看 CPU 使用率的变化情况

```
# -P ALL 表示监控所有CPU，后面数字5表示间隔5秒后输出一组数据
[root@localhost ~]# mpstat -P ALL 5 1
Linux 3.10.0-1160.24.1.el7.x86_64 (localhost.localdomain) 	01/12/2022 	_x86_64_	(2 CPU)

12:19:36 PM  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
12:19:41 PM  all    0.00    0.00   30.94    0.00    0.00    0.00    0.00    0.00    0.00   69.06
12:19:41 PM    0    0.00    0.00   40.54    0.00    0.00    0.00    0.00    0.00    0.00   59.46
12:19:41 PM    1    0.00    0.00   18.34    0.00    0.00    0.00    0.00    0.00    0.00   81.66

Average:     CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
Average:     all    0.00    0.00   30.94    0.00    0.00    0.00    0.00    0.00    0.00   69.06
Average:       0    0.00    0.00   40.54    0.00    0.00    0.00    0.00    0.00    0.00   59.46
Average:       1    0.00    0.00   18.34    0.00    0.00    0.00    0.00    0.00    0.00   81.66
[root@localhost ~]# 


```

%iowait的值过高，表示硬盘存在I/O瓶颈，%idle值高，表示CPU较空闲
原因:
iowait 无法升高是因为案例中 stress -i 使用的是 sync() 系统调用，它的作用是刷新缓冲区内存到磁盘中
对于虚拟机，缓冲区可能比较小，无法产生大的io压力
这样大部分都是系统调用的消耗了
所以，只看到系统 CPU 使用率升高



解决方案:
使用 stress 的另一个参数 -d
[root@centos7-2 ~]# stress -i 1 --timeout 600
stress: info: [8046] dispatching hogs: 0 cpu, 1 io, 0 vm, 0 hdd

# -d 参数表示高亮显示变化的区域
[root@centos7-2 ~]# watch -d uptime
11:17:08 up 33 min, 3 users, load average: 1.11, 0.91, 0.61
# -P ALL 表示监控所有CPU，后面数字5表示间隔5秒后输出一组数据
[root@centos7-2 ~]# mpstat -P ALL 5 1
Linux 3.10.0-693.el7.x86_64 (centos7-2) 2020年11月24日 _x86_64_ (2 CPU)
11时16分19秒 CPU %usr %nice %sys %iowait %irq %soft %steal %guest %gnice
%idle
11时16分24秒 all 0.31 0.00 42.19 0.00 0.00 0.00 0.00 0.00 0.00
57.50
11时16分24秒 0 0.21 0.00 26.35 0.00 0.00 0.00 0.00 0.00 0.00
73.44
11时16分24秒 1 0.42 0.00 58.16 0.00 0.00 0.00 0.00 0.00 0.00
41.42
平均时间: CPU %usr %nice %sys %iowait %irq %soft %steal %guest %gnice %idle
平均时间: all 0.31 0.00 42.19 0.00 0.00 0.00 0.00 0.00 0.00 57.50
平均时间: 0 0.21 0.00 26.35 0.00 0.00 0.00 0.00 0.00 0.00 73.44
平均时间: 1 0.42 0.00 58.16 0.00 0.00 0.00 0.00 0.00 0.00 41.42

```
# --hdd N 产生 N 个进程每个进程执行 write() 和 unlink() 的进程

# --hdd-bytes B 每个 hdd worker 写入 B 字节（默认为1GB）
[root@centos7-2 ~]# stress --hdd 1 -t 600 --hdd-bytes 4G
stress: info: [8710] dispatching hogs: 0 cpu, 0 io, 0 vm, 1 hdd



[root@localhost ~]# mpstat -P ALL 5 1
Linux 3.10.0-1160.24.1.el7.x86_64 (localhost.localdomain) 	01/12/2022 	_x86_64_	(2 CPU)

12:22:00 PM  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
12:22:05 PM  all    0.00    0.00    8.03   27.98    0.00    3.67    0.00    0.00    0.00   60.32
12:22:05 PM    0    0.00    0.00   12.55   40.38    0.00    0.21    0.00    0.00    0.00   46.86
12:22:05 PM    1    0.00    0.00    2.54   12.94    0.00    7.87    0.00    0.00    0.00   76.65

Average:     CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
Average:     all    0.00    0.00    8.03   27.98    0.00    3.67    0.00    0.00    0.00   60.32
Average:       0    0.00    0.00   12.55   40.38    0.00    0.21    0.00    0.00    0.00   46.86
Average:       1    0.00    0.00    2.54   12.94    0.00    7.87    0.00    0.00    0.00   76.65
[root@localhost ~]# 


```

那么到底是哪个进程，导致 iowait 这么高呢？我们还是用 pidstat 来查询：

```
[root@localhost ~]# pidstat -u 5 1
Linux 3.10.0-1160.24.1.el7.x86_64 (localhost.localdomain) 	01/12/2022 	_x86_64_	(2 CPU)

12:22:50 PM   UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
12:22:55 PM     0         6    0.00    0.20    0.00    0.40    0.20     0  ksoftirqd/0
12:22:55 PM     0         9    0.00    0.20    0.00    0.40    0.20     0  rcu_sched
12:22:55 PM     0        11    0.00    0.20    0.00    0.00    0.20     0  watchdog/0
12:22:55 PM     0        14    0.00    5.38    0.00    4.98    5.38     1  ksoftirqd/1
12:22:55 PM     0        36    0.00    3.39    0.00    0.00    3.39     1  kswapd0
12:22:55 PM     0       333    0.00    1.00    0.00    0.20    1.00     1  kworker/1:1H
12:22:55 PM     0       409    0.00    0.80    0.00    0.20    0.80     1  xfsaild/dm-0
12:22:55 PM     0      2444    0.00    4.38    0.00    4.38    4.38     1  kworker/1:3
12:22:55 PM     0      7204    0.00    6.37    0.00    1.79    6.37     1  kworker/u4:0

Average:      UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
Average:        0         6    0.00    0.20    0.00    0.40    0.20     -  ksoftirqd/0
Average:        0         9    0.00    0.20    0.00    0.40    0.20     -  rcu_sched
Average:        0        11    0.00    0.20    0.00    0.00    0.20     -  watchdog/0
Average:        0        14    0.00    5.38    0.00    4.98    5.38     -  ksoftirqd/1
Average:        0        36    0.00    3.39    0.00    0.00    3.39     -  kswapd0
Average:        0       333    0.00    1.00    0.00    0.20    1.00     -  kworker/1:1H
Average:        0       409    0.00    0.80    0.00    0.20    0.80     -  xfsaild/dm-0
Average:        0      2444    0.00    4.38    0.00    4.38    4.38     -  kworker/1:3
Average:        0      7204    0.00    6.37    0.00    1.79    6.37     -  kworker/u4:0
[root@localhost ~]# 


```



场景三:大量进程的场景

当系统中运行进程超出CPU运行能力时，就会出现等待CPU的进程。
比如，我们还是使用stress，但这次模拟的 是8个进程：

窗口1

```
[root@centos7-2 ~]#stress -c 8 --timeout 600
stress: info: [15985] dispatching hogs: 8 cpu, 0 io, 0 vm, 0 hdd窗口2
```

窗口2

```
[root@centos7-2 ~]# watch -d uptime
16:07:06 up 4:17, 3 users, load average: 1.09, 0.37, 0.80
```

窗口3
可以看出，8 个进程在争抢 2 个 CPU，每个进程等待CPU 的时间（也就是代码块中的 %wait 列）高达 75%这些超
出 CPU 计算能力的进程，最终导致 CPU 过载。

```
[root@localhost ~]# pidstat -u 5 1
Linux 3.10.0-1160.24.1.el7.x86_64 (localhost.localdomain) 	01/12/2022 	_x86_64_	(2 CPU)

06:52:14 PM   UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
06:52:19 PM     0      7383   17.17    0.20    0.00  102.99   17.37     0  stress
06:52:19 PM     0      7384   16.97    0.20    0.00   82.63   17.17     0  stress
06:52:19 PM     0      7385   28.34    0.80    0.00  110.98   29.14     0  stress
06:52:19 PM     0      7386   36.33    0.20    0.00   63.27   36.53     1  stress
06:52:19 PM     0      7387   29.14    0.20    0.00  111.18   29.34     1  stress
06:52:19 PM     0      7388   16.97    0.20    0.00  103.19   17.17     1  stress
06:52:19 PM     0      7389   35.33    1.00    0.00   63.27   36.33     0  stress
06:52:19 PM     0      7390   17.17    0.00    0.00   83.03   17.17     1  stress

Average:      UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
Average:        0      7383   17.17    0.20    0.00  102.99   17.37     -  stress
Average:        0      7384   16.97    0.20    0.00   82.63   17.17     -  stress
Average:        0      7385   28.34    0.80    0.00  110.98   29.14     -  stress
Average:        0      7386   36.33    0.20    0.00   63.27   36.53     -  stress
Average:        0      7387   29.14    0.20    0.00  111.18   29.34     -  stress
Average:        0      7388   16.97    0.20    0.00  103.19   17.17     -  stress
Average:        0      7389   35.33    1.00    0.00   63.27   36.33     -  stress
Average:        0      7390   17.17    0.00    0.00   83.03   17.17     -  stress
[root@localhost ~]# 

```

可以看出，8 个进程在争抢 2 个 CPU，每个进程等待CPU 的时间（也就是代码块中的 %wait 列）高达 75%这些超
出 CPU 计算能力的进程，最终导致 CPU 过载。

上下文切换
2.2.2.1 如何理解上下文切换
Linux 是一个多任务操作系统，它支持远大于 CPU 数量的任务同时运行，这是通过频繁的上下文切换、将CPU轮流
分配给不同任务从而实现的。

每个进程运行时，CPU都需要知道进程已经运行到了哪里以及当前的各种状态，因此系统事先设置好 CPU 寄存器和
程序计数器。CPU 上下文切换，就是先把前一个任务的 CPU 上下文（CPU 寄存器和程序计数器）保存起来，然后加载
新任务的上下文到这些寄存器和程序计数器，最后再跳转到程序计数器所指的新位置，运行新任务，而保存下来的上下文，
会存储在系统内核中，并在任务重新调度执行时再次加载进来。
根据 Tsuna 的测试报告，每次上下文切换都需要几十纳秒到到微秒的CPU时间，因此如果进程上下文切换次数过
多，就会导致 CPU 将大量时间耗费在寄存器、内核栈以及虚拟内存等资源的保存和恢复上，进而大大缩短了真正
运行进程的时间，实际上有效的CPU运行时间大大减少(可以认为上下文切换对用户来说是在做无用功)。



2.2.2.2 上下文切换的时机
根据调度策略，将CPU时间划片为对应的时间片，当时间片耗尽，就需要进行上下文切换
进程在系统资源不足，会在获取到足够资源之前进程挂起
进程通过sleep函数将自己挂起
当有优先级更高的进程运行时，为了保证高优先级进程的运行，当前进程会被挂起，由高优先级进程来运行,
也就是被抢占
当发生硬件中断时，CPU 上的进程会被中断挂起，转而执行内核中的中断服务程序



2.2.2.3 上下文切换分类
我们之前讲过的任务到底是什么呢？
进程和线程是最常见的任务
硬件通过触发信号，会导致中断处理程序的调用，也是一种常见的任务
所以，根据任务的不同，CPU 的上下文切换可以分为不同的场景
进程上下文切换
线程上下文切换
中断上下文切换



Linux 按照特权等级划分进程的运行空间
内核空间（Ring 0）：具有最高权限，可以直接访问所有资源
用户空间（Ring 3）：只能访问受限资源，不能直接访问内存等硬件设备，必须通过系统调用陷入到内核中，
才能访问这些特权资源

进程既可以在用户空间运行，又可以在内核空间中运行。进程在用户空间运行时，被称为进程的用户态，而陷入内
核空间的时候，被称为进程的内核态。 从用户态到内核态的转变，需要通过系统调用来完成。



系统调用举例:
当我们查看文件内容时， 需要多次系统调用来完成：
1. 首先调用 open() 打开文件，

2. 然后调用 read() 读取文件内容，

3. 并调用 write() 将内容写到标准输出，

4. 最后再调用 close() 关闭文件。
  系统调用的过程有没有发生 CPU 上下文的切换呢？答案自然是肯定的

5. CPU 寄存器里原来用户态的指令位置，需要先保存起来

6. 为了执行内核态代码，CPU 寄存器需要更新为内核态指令的新位置

7. 最后才是跳转到内核态运行内核任务

8. 系统调用结束后，CPU 寄存器需要恢复原来保存的用户态

9. 然后再切换回用户空间，继续运行进程

10. 系统调用和进程上下文切换的不同
    进程上下文切换：从一个进程切换到另一个进程运行
    系统调用：一直是同一个进程在运行
    系统调用过程通常称为特权模式切换，而不是上下文切换
    系统调用过程中， CPU 上下文切换是无法避免的

    2.进程上下文切换
    在 Linux 中，进程是由内核来管理和调度进程的，切换只能发生在内核态，进程的上下文不仅包括了 虚拟内存、
    栈、全局变量等用户空间的资源，还包括了内核堆栈、寄存器等内核空间的资源。
    进程上下文切换:
    在保存当前进程的内核状态和 CPU 寄存器之前，需要先把该进程的虚拟内存、栈等保存下来【保存上下文】
    而加载了下一进程的内核态后，还需要刷新进程的虚拟内存和用户栈【加载上下文】

11. 什么时候会切换进程上下文
    顾名思义，只有在进程切换时才需要切换上下文
    换句话说，只有在进程调度时才需要切换上下文

CPU 如何挑选进程来运行？
Linux 为每个 CPU 都维护了一个等待队列
将活跃进程（正在运行和正在等待 CPU 的进程）按照优先级和等待 CPU 的时间排序
然后选择最需要 CPU 的进程，也就是优先级最高和等待 CPU 时间最长的进程来运行
进程上下文切换如何影响系统性能
每次上下文切换都需要几十纳秒到数微秒的 CPU 时间。这个时间还是相当可观的，特别是在进程上下文切换次数
较多的情况下，很容易导致 CPU 将大量时间耗费在寄存器、内核栈以及虚拟内存等资源的保存和恢复上，进而大
大缩短了真正运行进程的时间。这也正是上一节中我们所讲的，导致平均负载升高的一个重要因素







2.2.3.4 CPU使用率过高怎么办
分析思路
1、如何轻松找到CPU使用率过高的进程
通过top、ps 、pidstat等工具
2、占用CPU高的到底是代码里的那个函数？
perf和GDB
GDB（The GNU Project Debugger）， 这个功能强大的程序调试利器 ，GDB 调试程序的过程会中断程序运行，这在
线上环境往往是不允许的 ；
perf 是 Linux 2.6.31 以后内置的性能分析工具。它以性能事件采样为基础，不仅可以分析系统的各种事件和内核性
能，还可以用来分析指定应用程序的性能问题 ，使用 perf 分析 CPU 性能问题，我来说两种用法
第一种常见用法是 perf top，类似于 top，它能够实时显示占用 CPU 时钟最多的函数或者指令，因此可以用来查找
热点函数，使用界面如下所示：
