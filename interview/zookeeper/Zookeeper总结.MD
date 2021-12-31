## 1: zookeeper的节点类型：
* 1、PERSISTENT
持久化目录节点
客户端与zookeeper断开连接后，该节点依旧存在，只要不手动删除该节点，他将永远存在
* 2、 PERSISTENT_SEQUENTIAL
持久化顺序编号目录节点
客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号
* 3、EPHEMERAL
临时目录节点
客户端与zookeeper断开连接后，该节点被删除
* 4、EPHEMERAL_SEQUENTIAL
临时顺序编号目录节点
客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号
* 5、 Container 节点（3.5.3 版本新增，如果Container节点下面没有子节点，则Container节点
在未来会被Zookeeper自动清除,定时任务默认60s 检查一次）
* 6、 TTL 节点( 默认禁用，只能通过系统配置 zookeeper.extendedTypesEnabled=true 开启，不稳
定)
![](images\zk节点类型.jpg)

## 2： 监听通知机制
[代码地址]: ../../zookeeper/zookeeper-springboot/src/main/java/com/ithawk/demo/zookeeper/springboot/utils/ZookeeperUtils.java	"zookeeper操作工具类"

客户端注册监听它关心的任意节点，或者目录节点及递归子目录节点

1. 如果注册的是对某个节点的监听，则当这个节点被删除，或者被修改时，对应的客户端将被通
知
2. 如果注册的是对某个目录的监听，则当这个目录有子节点被创建，或者有子节点被删除，对应
的客户端将被通知
3. 如果注册的是对某个目录的递归子节点进行监听，则当这个目录下面的任意子节点有目录结构
的变化（有子节点被创建，或被删除）或者根节点有数据变化时，对应的客户端将被通知。
4. 注意：所有的通知都是一次性的，及无论是对节点还是对目录进行的监听，一旦触发，对应的监
听即被移除。递归子节点，监听是对所有子节点的，所以，每个子节点下面的事件同样只会被触
发一次。