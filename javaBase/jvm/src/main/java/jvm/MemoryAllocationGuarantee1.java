package jvm;

/**
 * 内存分配担保案例:内存分配担保机制。就是当在新生代无法分配内存的时候，把新生代的对象转移到老生代，然后把新对象放入腾空的新生代。
 *
 *
 */
public class MemoryAllocationGuarantee1 {

	private static final int _30MB = 1024 * 1024*30;
	private static final int _10MB = 1024 * 1024*10;
/*<p>
内存分配担保机制。就是当在新生代无法分配内存的时候，把新生代的对象转移到老生代，然后把新对象放入腾空的新生代。
* -XX:+PrintFlagsInitial : 查看所有的参数的默认初始值
 * -XX:+PrintFlagsFinal  ：查看所有的参数的最终值（可能会存在修改，不再是初始值）
 *      具体查看某个参数的指令： jps：查看当前运行中的进程
 *                           jinfo -flag SurvivorRatio 进程id
 *
 * -Xms：初始堆空间内存 （默认为物理内存的1/64）
 * -Xmx：最大堆空间内存（默认为物理内存的1/4）
 * -Xmn：设置新生代的大小。(初始值及最大值)
 * -XX:NewRatio：配置新生代与老年代在堆结构的占比
 * -XX:SurvivorRatio：设置新生代中Eden和S0/S1空间的比例
 * -XX:MaxTenuringThreshold：设置新生代垃圾的最大年龄
 * -XX:+PrintGCDetails：输出详细的GC处理日志
 * 打印gc简要信息：① -XX:+PrintGC   ② -verbose:gc
 * -XX:HandlePromotionFailure：是否设置空间分配担保
 * -Xloggc:d:\gc.log      gc打印到gc.log中
vm 参数： -Xms20M
         -Xmx20M
         -Xmn10M
         -XX:+PrintGCTimeStamps
         -XX:+PrintGCDetails
         -Xloggc:d:\gc.log
         -XX:SurvivorRatio=8
         -XX:+UseSerialGC
         -XX:HandlePromotionFailure  JDK7及以后这个参数就失效了。   变成 PromotionFailureALot
 */
	public static void memoryAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _10MB];
//		allocation2 = new byte[2 * _10MB];
//		allocation3 = new byte[2 * _10MB];
//		allocation4 = new byte[3 * _30MB];
	}

	public static void main(String[] args) {
		memoryAllocation();
	}
}
