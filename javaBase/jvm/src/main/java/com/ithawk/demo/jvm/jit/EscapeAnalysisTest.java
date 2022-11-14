package com.ithawk.demo.jvm.jit;

/**	 <p>
 *  添加jvm运行参数：
 *   -XX:+DoEscapeAnalysis ： 表示开启逃逸分析
 *   -XX:-DoEscapeAnalysis ： 表示关闭逃逸分析
 *
 *
 * </p>
 * @className TestLockEliminate
 * @description:  逃逸分析： 栈上内存
 * @author IThawk
 * @date 2021/8/3 9:48
 */
public class EscapeAnalysisTest {
	private int age = 19;
	public static void main(String[] args) {
		long a1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			alloc();
		}
		// 查看执行时间
		long a2 = System.currentTimeMillis();
		System.out.println("cost " + (a2 - a1) + " ms");
		// 为了方便查看堆内存中对象个数，线程sleep

		//1:-Xmx4G -Xms4G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
		//jmap -histo 2809
		//  2:        125365        2005840  com.ithawk.demo.jvm.jit.EscapeAnalysisTest$User

		// 2: -Xmx4G -Xms4G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
		// 2:       1000000       16000000  com.ithawk.demo.jvm.jit.EscapeAnalysisTest$User
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private static void alloc() {
		//
		User user = new User();
	}

	static class User {
	}
}
