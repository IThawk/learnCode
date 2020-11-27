# 什么叫事务传播行为？听起来挺高端的，其实很简单。
    即然是传播，那么至少有两个东西，才可以发生传播。单体不存在传播这个行为。

## 事务传播行为（propagation behavior）指的就是当一个事务方法被另一个事务方法调用时，这个事务方法应该如何进行。
    例如：methodA事务方法调用methodB事务方法时，methodB是继续在调用者methodA的事务中运行呢，还是为自己开启一个新事务运行，这就是由methodB的事务传播行为决定的。

## Spring定义了七种传播行为：

### 1、PROPAGATION_REQUIRED
    如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。
    可以把事务想像成一个胶囊，在这个场景下方法B用的是方法A产生的胶囊（事务）。
    这里写图片描述
    
    举例有两个方法：
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
     methodB();
    // do something
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodB() {
        // do something
    }
    
    单独调用methodB方法时，因为当前上下文不存在事务，所以会开启一个新的事务。
    调用methodA方法时，因为当前上下文不存在事务，所以会开启一个新的事务。当执行到methodB时，methodB发现当前上下文有事务，因此就加入到当前事务中来。

### 2、PROPAGATION_SUPPORTS
    如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行。但是对于事务同步的事务管理器，PROPAGATION_SUPPORTS与不使用事务有少许不同。
    举例有两个方法：
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
     methodB();
    // do something
    }
    
    // 事务属性为SUPPORTS
    @Transactional(propagation = Propagation.SUPPORTS)
    public void methodB() {
        // do something
    }
    
    单纯的调用methodB时，methodB方法是非事务的执行的。当调用methdA时,methodB则加入了methodA的事务中,事务地执行。

### 3、PROPAGATION_MANDATORY
    如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
     methodB();
    // do something
    }
    
    // 事务属性为MANDATORY
    @Transactional(propagation = Propagation.MANDATORY)
    public void methodB() {
        // do something
    }
    
    当单独调用methodB时，因为当前没有一个活动的事务，则会抛出异常throw new IllegalTransactionStateException(“Transaction propagation ‘mandatory’ but no existing transaction found”);当调用methodA时，methodB则加入到methodA的事务中，事务地执行。

### 4、PROPAGATION_MANDATORY

    使用PROPAGATION_REQUIRES_NEW,需要使用 JtaTransactionManager作为事务管理器。
    它会开启一个新的事务。如果一个事务已经存在，则先将这个存在的事务挂起。
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void methodA() {
    doSomeThingA();
    methodB();
    doSomeThingB();
    // do something else
    }
    
    
    // 事务属性为REQUIRES_NEW
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void methodB() {
        // do something
    }
    
    
    当调用
    
    main{  
    methodA();
    } 
    
    相当于调用
    
    main(){
        TransactionManager tm = null;
        try{
            //获得一个JTA事务管理器
            tm = getTransactionManager();
            tm.begin();//开启一个新的事务
            Transaction ts1 = tm.getTransaction();
            doSomeThing();
            tm.suspend();//挂起当前事务
            try{
                tm.begin();//重新开启第二个事务
                Transaction ts2 = tm.getTransaction();
                methodB();
                ts2.commit();//提交第二个事务
            } Catch(RunTimeException ex) {
                ts2.rollback();//回滚第二个事务
            } finally {
                //释放资源
            }
            //methodB执行完后，恢复第一个事务
            tm.resume(ts1);
            doSomeThingB();
            ts1.commit();//提交第一个事务
        } catch(RunTimeException ex) {
            ts1.rollback();//回滚第一个事务
        } finally {
            //释放资源
        }
    }
    
    在这里，我把ts1称为外层事务，ts2称为内层事务。从上面的代码可以看出，ts2与ts1是两个独立的事务，互不相干。Ts2是否成功并不依赖于 ts1。如果methodA方法在调用methodB方法后的doSomeThingB方法失败了，而methodB方法所做的结果依然被提交。而除了 methodB之外的其它代码导致的结果却被回滚了

### 5、PROPAGATION_NOT_SUPPORTED
    PROPAGATION_NOT_SUPPORTED 总是非事务地执行，并挂起任何存在的事务。使用PROPAGATION_NOT_SUPPORTED,也需要使用JtaTransactionManager作为事务管理器。


### 6、PROPAGATION_NEVER
    总是非事务地执行，如果存在一个活动事务，则抛出异常。

### 7、PROPAGATION_NESTED

    如果一个活动的事务存在，则运行在一个嵌套的事务中。 如果没有活动事务, 则按TransactionDefinition.PROPAGATION_REQUIRED 属性执行。
    这是一个嵌套事务,使用JDBC 3.0驱动时,仅仅支持DataSourceTransactionManager作为事务管理器。
    需要JDBC 驱动的java.sql.Savepoint类。使用PROPAGATION_NESTED，还需要把PlatformTransactionManager的nestedTransactionAllowed属性设为true(属性值默认为false)。
    
    这里关键是嵌套执行。
    
    @Transactional(propagation = Propagation.REQUIRED)
    methodA(){
      doSomeThingA();
      methodB();
      doSomeThingB();
    }
    
    @Transactional(propagation = Propagation.NEWSTED)
    methodB(){
      ……
    }
    
    如果单独调用methodB方法，则按REQUIRED属性执行。如果调用methodA方法，相当于下面的效果：
    
    main(){
        Connection con = null;
        Savepoint savepoint = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            doSomeThingA();
            savepoint = con2.setSavepoint();
            try{
                methodB();
            } catch(RuntimeException ex) {
                con.rollback(savepoint);
            } finally {
                //释放资源
            }
            doSomeThingB();
            con.commit();
        } catch(RuntimeException ex) {
            con.rollback();
        } finally {
            //释放资源
        }
    }
    
    当methodB方法调用之前，调用setSavepoint方法，保存当前的状态到savepoint。如果methodB方法调用失败，则恢复到之前保存的状态。但是需要注意的是，这时的事务并没有进行提交，如果后续的代码(doSomeThingB()方法)调用失败，则回滚包括methodB方法的所有操作。嵌套事务一个非常重要的概念就是内层事务依赖于外层事务。外层事务失败时，会回滚内层事务所做的动作。而内层事务操作失败并不会引起外层事务的回滚。
## 总结
    PROPAGATION_NESTED 与PROPAGATION_REQUIRES_NEW的区别:
    它们非常类似,都像一个嵌套事务，如果不存在一个活动的事务，都会开启一个新的事务。
    使用 PROPAGATION_REQUIRES_NEW时，内层事务与外层事务就像两个独立的事务一样，一旦内层事务进行了提交后，外层事务不能对其进行回滚。两个事务互不影响。两个事务不是一个真正的嵌套事务。同时它需要JTA事务管理器的支持。
    
    使用PROPAGATION_NESTED时，外层事务的回滚可以引起内层事务的回滚。而内层事务的异常并不会导致外层事务的回滚，它是一个真正的嵌套事务。DataSourceTransactionManager使用savepoint支持PROPAGATION_NESTED时，需要JDBC 3.0以上驱动及1.4以上的JDK版本支持。其它的JTATrasactionManager实现可能有不同的支持方式。
    
    PROPAGATION_REQUIRES_NEW 启动一个新的, 不依赖于环境的 “内部” 事务. 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等. 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行。
    
    另一方面, PROPAGATION_NESTED 开始一个 “嵌套的” 事务, 它是已经存在事务的一个真正的子事务. 潜套事务开始执行时, 它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交。
    
    由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于, PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 嵌套事务也会被 commit, 这个规则同样适用于 roll back.
    
### 脏读：
    一个事务对数据进行了增、删、改，但未提交，另一个事务可以读取到未提交的数据。
    如果第一个事务这时候回滚了，那么第二个事务就读到了脏数据。
### 不可重复读：
    一个事务中发生了两次读操作，在第一次读操作和第二次读操作之间， 另外一
    个事务对数据进行了修改，这时候两次读取的数据是不一致的。
### 幻读：
    第一个事务对一定范围的数据进行了批量修改，第二个事务在这个范围内增加了一条
    数据，这时候第一个事务就会丢失对新增数据的修改。
    数据库事务隔离级别越高，越能保证数据的完整性和一致性，但是对井发性能的影响也越大。
    大多数数据库（比如SQLServer和Oracle）事务默认隔离级别为Read-Commited，少数数据库（比
    如MySQLInnoDB） 事务默认隔离级别为Repeatable-Read。
### 事务的嵌套
    假设外部事务ServiceA的MethodA（）调用内部事务ServiceB的MethodB（）。
#### 1. PROPAGATION_REQUIRED (Spring默认事务属性）
    如果ServiceB.MethodB（）的事务属性定义为PROPAGATION_REQUIRED， 那么执行
    ServiceA.MethodA（）的时候Spring已经发起了事务，这时调用ServiceB.MethodB(),ServiceB.MethodB() 
    看到自己已经运行在ServiceA.MethodA（）的事务内部， 就不再发起新的事务。
    假如ServiceB.MethodB（）运行的时候发现自己没有在事务中，它就会为自己分配一个事务。
    这样，在ServiceA.MetbodA（）或者ServiceB.MethodB（）内的任何地方出现异常， 事务都会被
    回滚。
#### 2. PROPAGATION_REQUIRES_NEW 
    如果设计ServiceA.MethodAQ的事务属性为PROPAGATION_REQUIRED, ServiceB.MethodBQ的
    事务属性为PROPAGATION＿REQUIRES_NEW，那么当执行到ServiceB.MethodB（）的时候，
    ServiceA.MethodA（）所在的事务就会挂起，ServiceB.MethodB（）会发起一个新的事务，等待
    ServiceB.MethodB（）的事务完成以后，挂起的事务才会继续执行。
    它与PROPAGATION_REQUIRED的区别在于事务的回滚程度。因为ServiceB.MethodB（）新发
    起一个事务，存在两个不同的事务。 如果ServiceB.MethodBQ已经提交，那么ServiceA.MethodA()
    回滚失败时’ ServiceB.MethodB（）是不会回滚的。如果ServiceB.MethodB（）回滚失败，它抛出的异常
    被ServiceA.MethodA（）捕获， ServiceA.MethodA（）的事务仍然可能提交（主要看ServiceBMethodB() 
    抛出的异常是不是ServiceA.MethodA（）会回滚的异常〉。
#### 3. PROPAGATION_SUPPORTS 
    假设ServiceB.MethodB（）的事务属性为PROPAGATION_SUPPORTS，那么当执行到
    ServiceB.MethodB（）时，如果发现ServiceA.MethodA（）己经开启了一个事务，则加入当前的事务。
    如果发现ServiceA.MethodA（）没有开启事务，则自己也不开启事务。 对于这种事务属性， 内部方法
    的事务完全依赖于最外部的事务。
#### 4. PROPAGATION_NESTED 
    这种情况比较复杂，ServiceB.MethodB（）的事务属性被配置为PROPAGATION＿阳STED，此
    时两者之间将如何协作呢？ ServiceB.MethodB（）如果回滚，那么内部事务（即ServiceB.MethodB())
    将回滚到它执行前的SavePoint， 而外部事务（即ServiceA.MethodA（））可以有以下两种处理方式。
    (1） 捕获异常，执行异常分支逻辑
    void MethodA() { 
        try  { 
            ServiceB.methodB();
        } catch  (SomeException)  { 
            ／／ 执行其他事务，如ServiceC.MethodC();
        }
    这种方式也是嵌套事务最有价值的地方，它起到了分支执行的效果，如果ServiceB.MethodB() 
    失败，那么执行ServiceC.MethodC（）， 而ServiceB.MethodB（）己经回滚到它执行之前的SavePoint,
    所以不会产生脏数据（相当于此方法从未执行过），这种特性可以用在某些特殊的业务中， 而
    PROPAGATION_REQUIRED和PROPAGATION_REQUIRES_NEW都没有办法做到这一点。
    (2） 外部事务回滚／提交
    代码不做任何修改，如果内部事务（ServiceB .MethodB（））回滚， 首先ServiceB.MethodB（）回
    滚到它执行之前的SavePoint （在任何情况下都会如此），外部事务（即ServiceA.MethodA（））将
    根据具体的配置决定自己是提交还是回滚。
另外三种事务传播属性基本用不到， 在此不做分析。    