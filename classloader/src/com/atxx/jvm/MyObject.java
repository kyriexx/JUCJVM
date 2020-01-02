package com.atxx.jvm;

import java.util.concurrent.*;

/**
 * @author Kyrie
 * @create 2019-12-28 14:14
 *
 * echo %JAVA_HOME%   D:\Java\jdk1.8.0_211
 *
 * 虚拟机自带的加载器
 * 启动类加载器（Bootstrap）C++     $JAVAHOME/jre/lib/rt.jar
 * 扩展类加载器（Extension）Java    $JAVAHOME/jre/lib/ext/*.jar
 * 应用程序类加载器（AppClassLoader）Java也叫系统类加载器，加载当前应用的classpath的所有类
 *
 * 用户自定义加载器  Java.lang.ClassLoader的子类，用户可以定制类的加载方式
 */
public class MyObject {

    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(object.getClass().getClassLoader());//null


        // sun.misc.Launcher JVM相关调用的入口程序
        MyObject myObject = new MyObject();
        System.out.println(myObject.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(myObject.getClass().getClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader@1b6d3586
        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());//null




        /*
        //3.实现callable接口
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new Callable() {
            @Override
            public String call() throws Exception {
                return "通过实现Callable接口";
            }
        });
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */


    }
}
