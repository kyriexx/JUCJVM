package com.luban.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Kyrie
 * @create 2020-01-10 11:43
 */
public class MyInvocationHandler implements InvocationHandler {

    // 目标对象
    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    /**
     * 当我们通过代理类的对象，调用方法a时，就会自动的调用如下的方法：invoke()
     * 因为上面返回的代理类的对象有一个参数是InvocationHandler类型的对象(handler)，
     * 当调用代理类的方法时，就会根据这个对象，找到这个对象的invoke()方法
     *
     * 将被代理类要执行的方法a的功能就声明在invoke()中
     *
     * 参数Object proxy：就是代理类的对象，就是getProxyInstance返回的对象
     * 参数Method method：就是这个代理类调用的是什么方法，这个method就是那个方法
     * @param proxy 代理类的对象
     * @param method 这个代理类调用的是什么方法，这个method就是那个方法
     * @param args 方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("哈哈哈");
        return method.invoke(target,args);
    }
}
