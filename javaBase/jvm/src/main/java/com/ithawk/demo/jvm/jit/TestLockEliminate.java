package com.ithawk.demo.jvm.jit;

/**
 * @className TestLockEliminate
 * @description:  逃逸分析： 锁销除
 * @author IThawk
 * @date 2021/8/3 9:48
 */
public class TestLockEliminate {
	public static String getString(String s1, String s2) {
		StringBuffer sb = new StringBuffer();
		sb.append(s1);
		sb.append(s2);
		return sb.toString();
	}

	/**<p>
	 * 添加jvm运行参数：
	 *   -XX:+DoEscapeAnalysis ： 表示开启逃逸分析   一共耗费：3609 ms
	 *   -XX:-DoEscapeAnalysis ： 表示关闭逃逸分析:  一共耗费：4400 ms
	 *   -XX:+DoEscapeAnalysis -XX:+EliminateLocks   ： 开启锁消除：      一共耗费：3386 ms
	 *   -XX:+DoEscapeAnalysis -XX:-EliminateLocks   ： 关闭锁消除：      一共耗费：4444 ms
	 *   -XX:-DoEscapeAnalysis -XX:-EliminateLocks   ： 关闭锁消除：      一共耗费：4952 ms
	 * </p>
	 * @description:
	  * @param args
	 * @return: void
	 * @author IThawk
	 * @date: 2021/8/3 9:30
	 */
	public static void main(String[] args) {
		long tsStart = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			getString("TestLockEliminate ", "Suffix");
		}
		System.out.println("一共耗费：" + (System.currentTimeMillis() - tsStart) + " ms");
	//	System.gc();
	}
}
