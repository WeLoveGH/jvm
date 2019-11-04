package com.godtrue.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @description：
 * @author：qianyingjie1
 * @create：2019-11-04
 */
public class OccurOOMJavaHeapSpace {
    public static void main(String[] args) throws InterruptedException{
        Byte[] b;
        List list = new ArrayList();
        Integer count = 0;
        for(;;){
            Thread.sleep(5);
            System.out.println("OccurOOM " + count++);
            b = new Byte[1024 * 100];
            list.add(b);
        }
    }
}


/*

Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
at com.godtrue.jvm.OccurOOM.main(OccurOOM.java:19)
Java HotSpot(TM) 64-Bit Server VM warning: NewSize (1536k) is greater than the MaxNewSize (1024k). A new max generation size of 1536k will be used.

*/
