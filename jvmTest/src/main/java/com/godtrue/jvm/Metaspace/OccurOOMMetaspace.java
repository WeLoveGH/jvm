package com.godtrue.jvm.Metaspace;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @description： http://lovestblog.cn/blog/2016/10/29/metaspace/
 * @author：qianyingjie1
 * @create：2019-11-04
 */
public class OccurOOMMetaspace {

    /**
     * 元空间内存泄漏的映射
     */
    private static Map<String,MetaspaceFacade> classLeakingMap = new HashMap<String,MetaspaceFacade>();

    /**
     * 循环次数记录器
     */
    private static int count =0;
    /**
     * 1：打印元空间内存泄漏的开始
     * 2：通过一个死循环，不管的生成对应的动态代理类的字节码文件
     * 3：打印元空间内存泄漏的结束
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Class metadata leak simulator  please use -XX:+TraceClassLoading -XX:MaxMetaspaceSize=128m");

        try {

            for (;;) {

                String fictionClassLoaderJAR = "file:"+(count++)+".jar";

                System.out.println(fictionClassLoaderJAR);

                URL[] fictionClassloaderURL = new URL[]{new URL(fictionClassLoaderJAR)};

                URLClassLoader newClassLoader = new URLClassLoader(fictionClassloaderURL);

                MetaspaceFacade t = (MetaspaceFacade)Proxy.newProxyInstance(newClassLoader,
                        new Class<?>[]{MetaspaceFacade.class},
                        new MetaspaceFacadeInvocationHandler(new MetaspaceFacadeImpl()));

                classLeakingMap.put(fictionClassLoaderJAR,t);
            }

        }catch (Throwable t){
            t.printStackTrace();
        }

        System.out.println("Done!");
    }
}


/*

[GC (Metadata GC Threshold) [PSYoungGen: 243519K->25984K(412160K)] 317254K->99718K(649728K), 0.0413009 secs] [Times: user=0.12 sys=0.02, real=0.04 secs]
[Full GC (Metadata GC Threshold) [PSYoungGen: 25984K->0K(412160K)] [ParOldGen: 73734K->99625K(298496K)] 99718K->99625K(710656K), [Metaspace: 80809K->80809K(1138688K)], 0.3084249 secs] [Times: user=0.92 sys=0.00, real=0.31 secs]
[GC (Last ditch collection) [PSYoungGen: 0K->0K(504832K)] 99625K->99625K(803328K), 0.0029440 secs] [Times: user=0.06 sys=0.00, real=0.00 secs]
[Full GC (Last ditch collection) [PSYoungGen: 0K->0K(504832K)] [ParOldGen: 99625K->93259K(362496K)] 99625K->93259K(867328K), [Metaspace: 80809K->80654K(1138688K)], 0.5202979 secs] [Times: user=1.50 sys=0.00, real=0.52 secs]
[Loaded java.lang.ClassFormatError from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
java.lang.OutOfMemoryError: Metaspace
	at java.lang.reflect.Proxy.defineClass0(Native Method)
	at java.lang.reflect.Proxy.access$300(Proxy.java:228)
	at java.lang.reflect.Proxy$ProxyClassFactory.apply(Proxy.java:642)
	at java.lang.reflect.Proxy$ProxyClassFactory.apply(Proxy.java:557)
	at java.lang.reflect.WeakCache$Factory.get(WeakCache.java:230)
	at java.lang.reflect.WeakCache.get(WeakCache.java:127)
	at java.lang.reflect.Proxy.getProxyClass0(Proxy.java:419)
	at java.lang.reflect.Proxy.newProxyInstance(Proxy.java:719)
	at com.godtrue.jvm.Metaspace.OccurOOMMetaspace.main(OccurOOMMetaspace.java:47)
[Loaded java.lang.Throwable$PrintStreamOrWriter from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
[Loaded java.lang.Throwable$WrappedPrintStream from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
[Loaded java.util.IdentityHashMap$KeySet from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor33 from __JVM_DefineClass__]
Done!
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor34 from __JVM_DefineClass__]
[Loaded java.util.IdentityHashMap$IdentityHashMapIterator from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
[Loaded java.util.IdentityHashMap$KeyIterator from C:\Program Files\Java\jdk1.8.0_162\jre\lib\rt.jar]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor35 from __JVM_DefineClass__]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor36 from __JVM_DefineClass__]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor37 from __JVM_DefineClass__]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor38 from __JVM_DefineClass__]
[Loaded sun.reflect.GeneratedSerializationConstructorAccessor39 from __JVM_DefineClass__]
Heap
 PSYoungGen      total 504832K, used 20796K [0x00000007ba100000, 0x00000007daf80000, 0x00000007f8800000)
  eden space 475648K, 4% used [0x00000007ba100000,0x00000007bb54f3f0,0x00000007d7180000)
  from space 29184K, 0% used [0x00000007d9300000,0x00000007d9300000,0x00000007daf80000)
  to   space 31744K, 0% used [0x00000007d7180000,0x00000007d7180000,0x00000007d9080000)
 ParOldGen       total 362496K, used 93259K [0x000000073d200000, 0x0000000753400000, 0x00000007ba100000)
  object space 362496K, 25% used [0x000000073d200000,0x0000000742d12db0,0x0000000753400000)
 Metaspace       used 80707K, capacity 130722K, committed 131072K, reserved 1138688K
  class space    used 12280K, capacity 41474K, committed 41512K, reserved 1048576K

Process finished with exit code 0


*/