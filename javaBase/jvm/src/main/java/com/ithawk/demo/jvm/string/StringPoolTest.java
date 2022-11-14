package com.ithawk.demo.jvm.string;

public class StringPoolTest {

    public static void main(String[] args) {
        String str1 = "abc";
        System.out.println(str1 == "abc");//true
				/*<p>
		步骤：
			1) 栈中开辟一块空间存放引用str1，
			2) String池中开辟一块空间，存放String常量"abc"，
			3) 引用str1指向池中String常量"abc"，
			4) str1所指代的地址即常量"abc"所在地址，输出为true
		 */
        String str2 = new String("abc");
        System.out.println(str1 == str2);//false
		/*<p>
		步骤：
			1) 栈中开辟一块空间存放引用str3，
			2) 堆中开辟一块空间存放一个新建的String对象"abc"，
			3) 引用str3指向堆中的新建的String对象"abc"，
			4) str2所指代的对象地址为堆中地址，而常量"abc"地址在池中，输出为false
		 */

        String str3 = new String("abc");
        System.out.println(str3 == str2);//false
        System.out.println("str3 == str3.intern()%S"+str3 == str3.intern());//false
		/*<p>
		步骤：
            1) 栈中开辟一块空间存放引用str3，
            2) 堆中开辟一块新空间存放另外一个(不同于str2所指)新建的String对象，
            3) 引用str3指向另外新建的那个String对象
            4) str3和str2指向堆中不同的String对象，地址也不相同，输出为false
		 */

        String str4 = "a" + "b";
        System.out.println(str4 == "ab");//true
        /*<p>
        步骤：
            1) 栈中开辟一块空间存放引用str4，
            2) 根据编译器合并已知量的优化功能，池中开辟一块空间，存放合并后的String常量"ab"，
            3) 引用str4指向池中常量"ab"，
            4) str4所指即池中常量"ab"，输出为true
         */
        final String s = "a";
        String str5 = s + "b";
        System.out.println(str5 == "ab");//true

		/*<p>
        步骤：
            1) 栈中开辟一块空间存放引用str5，
            2) 根据编译器合并已知量的优化功能，池中开辟一块空间，存放合并后的String常量"ab"，
            3) 引用str5指向池中常量"ab"，
            4) str5所指即池中常量"ab"，输出为true
         */

        String s1 = "a";
        String s2 = "b";
        String str6 = s1 + s2;
        System.out.println(str6 == "ab");//false
        /*<p>
        步骤：
            1) 栈中开辟一块中间存放引用s1，s1指向池中String常量"a"，
            2) 栈中开辟一块中间存放引用s2，s2指向池中String常量"b"，
            3) 栈中开辟一块中间存放引用str6，
            4) s1 + s2通过StringBuilder的最后一步toString()方法还原一个新的String对象"ab"，因此
            堆中开辟一块空间存放此对象，
            5) 引用str6指向堆中(s1 + s2)所还原的新String对象，
            6) str6指向的对象在堆中，而常量"ab"在池中，输出为false
             */
        String str7 = "abc".substring(0, 2);
        System.out.println(str7 == "ab");//false
        /*<p>
        步骤：
            1) 栈中开辟一块空间存放引用str7，
            2) substring()方法还原一个新的String对象"ab"（不同于str6所指），堆中开辟一块空间存放此
            对象，
            3) 引用str7指向堆中的新String对象，
         */

        String str8 = "abc".toUpperCase();
        System.out.println(str8 == "ABC");//false
        /*<p>
        步骤：
            1) 栈中开辟一块空间存放引用str8，
            2) toUpperCase()方法还原一个新的String对象"ABC"，池中并未开辟新的空间存放String常
            量"ABC"，
            3) 引用str8指向堆中的新String对象
         */
        String s3 = "ab";
        String s4 = "ab" + getString();
        System.out.println(s3 == s4);//false

        String s5 = "a";
        String s6 = "abc";  //在常量池中
        String s7 = s5 + "bc";
        System.out.println(s6 == s7.intern());//true

        System.out.println(s6.intern()==s6);//true
		/*<p>
		intern的作用
		intern的作用是把new出来的字符串的引用添加到stringtable中，java会先计算string的
		hashcode，查找stringtable中是否已经有string对应的引用了，如果有返回引用（地址），然后没
		有把字符串的地址放到stringtable中，并返回字符串的引用（地址）。



		我们继续看例子：
		String a = new String("haha");
		System.out.println(a.intern() == a);//false

		因为有双引号括起来的字符串，所以会把ldc命令，即"haha"会被我们添加到字符串常量池，它
		的引用是string的char数组的地址，会被我们添加到stringtable中。所以a.intern的时候，
		返回的其实是string中的char数组的地址，和a的string实例化地址肯定是不一样的。

		String e = new String("jo") + new String("hn");
		System.out.println(e.intern() == e);//true
		new String("jo") + new String("hn")实际上会转为stringbuffer的append 然后
		tosring()出来，实际上是new 一个新的string出来。在这个过程中，并没有双引号括起john，
		也就是说并不会执行ldc然后把john的引用添加到stringtable中，所以intern的时候实际就是
		把新的string地址（即e的地址）添加到stringtable中并且返回回来。


		String f = new String("ja") + new String("va");
		System.out.println(f.intern() == f);//false
		或许很多朋友感觉很奇怪，这跟上面的例子2基本一模一样，但是却是false呢？这是因为java在
		启动的时候，会把一部分的字符串添加到字符串常量池中，而这个“java”就是其中之一。所以
		intern回来的引用是早就添加到字符串常量池中的”java“的引用，所以肯定跟f的原地址不一
		样。

		 */
        // 为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static String getString() {
        return "c";
    }
}
