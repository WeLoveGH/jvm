package com.godtrue.jvm;

/**
 * @description：
 * @author：qianyingjie1
 * @create：2019-11-04
 */
public class OccurStackOverflowError {
    private static int count = 0;
    private static void recursion(){
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        /*}catch (Exception e){*/
        }catch (Throwable e){
            System.out.println("count : "+count);
            e.printStackTrace();
        }
    }
}


/*

通过实验可以发现如下现象

1：通过递归的方式认为的制造 java.lang.StackOverflowError 这个异常，这是JVM方法栈内存溢出的异常
2：这也是一个需要注意的地方，如果使用方法递归调用，不注意，可能会出现这样的异常
3：捕获 Exception 对于JVM方法栈内存溢出的问题是不灵的，这种错误比较验证
4：捕获 Throwable 是可以的

count : 12565
java.lang.StackOverflowError
	at com.godtrue.jvm.OccurStackOverflowError.recursion(OccurStackOverflowError.java:11)
	at com.godtrue.jvm.OccurStackOverflowError.recursion(OccurStackOverflowError.java:12)


Exception in thread "main" java.lang.StackOverflowError
	at com.godtrue.jvm.OccurStackOverflowError.recursion(OccurStackOverflowError.java:12)

 */