package com.godtrue.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @description：
 * @author：qianyingjie1
 * @create：2019-11-04
 */
public class SetHeapSpace {
    private static Integer count = 0;
    private static List list = new ArrayList<Integer>();
    public static void main(String[] args) {
        for (;;) {
            System.out.println("JVM 参数 -Xmx128m -Xms128m -Xmn128m -XX:+PrintGCDetails -XX:MaxMetaspaceSize=128m 循环次数："+count++);
            list.add(count);
        }
    }
}

/*

循环次数：4102267
[Full GC (Ergonomics) [PSYoungGen: 84527K->82150K(114688K)] [ParOldGen: 504K->504K(512K)] 85032K->82655K(115200K), [Metaspace: 9512K->9512K(1058816K)], 0.7037666 secs] [Times: user=2.28 sys=0.00, real=0.70 secs]
[Full GC (Allocation Failure) [PSYoungGen: 82150K->81766K(114688K)] [ParOldGen: 504K->504K(512K)] 82655K->82271K(115200K), [Metaspace: 9512K->9357K(1058816K)], 0.8379776 secs] [Times: user=2.45 sys=0.03, real=0.84 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:231)
	at java.util.ArrayList.add(ArrayList.java:462)
	at com.godtrue.jvm.SetHeapSpace.main(SetHeapSpace.java:17)
Heap
 PSYoungGen      total 114688K, used 83308K [0x00000000f8080000, 0x0000000100000000, 0x0000000100000000)
  eden space 98816K, 84% used [0x00000000f8080000,0x00000000fd1db130,0x00000000fe100000)
  from space 15872K, 0% used [0x00000000ff080000,0x00000000ff080000,0x0000000100000000)
  to   space 15872K, 0% used [0x00000000fe100000,0x00000000fe100000,0x00000000ff080000)
 ParOldGen       total 512K, used 504K [0x00000000f8000000, 0x00000000f8080000, 0x00000000f8080000)
  object space 512K, 98% used [0x00000000f8000000,0x00000000f807e3c8,0x00000000f8080000)
 Metaspace       used 9411K, capacity 9656K, committed 9984K, reserved 1058816K
  class space    used 1099K, capacity 1163K, committed 1280K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: MaxNewSize (131072k) is equal to or greater than the entire heap (131072k).  A new max generation size of 130560k will be used.

Process finished with exit code 1

*/

