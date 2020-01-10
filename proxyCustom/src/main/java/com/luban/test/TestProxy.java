package com.luban.test;

import com.luban.dao.UserDao;
import com.luban.dao.UserDaoImpl;
import com.luban.extendsproxy.UserDaoAndLogImpl;
import com.luban.implproxy.UserDaoAndTimeImpl;
import com.luban.proxy.ProxyUtil;
import com.luban.util.MyInvocationHandler;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Kyrie
 * @create 2020-01-09 17:05
 */
public class TestProxy {

    @Test
    public void test01() {
        UserDaoAndLogImpl userDaoAndLog = new UserDaoAndLogImpl();
        userDaoAndLog.add(2, 8);
    }

    @Test
    public void test02() {

        UserDaoImpl userDao = new UserDaoImpl();
        UserDaoAndTimeImpl userDaoAndTime = new UserDaoAndTimeImpl(userDao);
        userDaoAndTime.add(2, 8);
    }


    @Test
    public void testCustomProxy() {
        //自己模拟的动态代理
        UserDao proxy = (UserDao) ProxyUtil.getInstance(new UserDaoImpl());
        String returnValue = proxy.query();
        System.out.println(returnValue);
    }

    @Test
    public void testJDKProxy() {
        //JDK的动态代理

        /*
        public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        */

        // 自己模拟的动态代理使用的是URLClassLoder，是因为产生的代理类不在当前工程中
        // 而jdk动态代理产生的代理类有可能在当前工程中，所以使用的是当前类的类加载器
        UserDao proxy = (UserDao) Proxy.newProxyInstance(TestProxy.class.getClassLoader(),
                new Class[]{UserDao.class},
                new MyInvocationHandler(new UserDaoImpl()));

        proxy.add(2,8);

    }

    @Test
    public void testReflect() {

        /*
        public interface UserDao {
            void add(int i,int j);
        }
        */

        UserDaoImpl target = new UserDaoImpl();
        Class targetClass = target.getClass();
        System.out.println(targetClass);// class com.luban.dao.UserDaoImpl
        System.out.println(targetClass.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2

        System.out.println(UserDao.class);//interface com.luban.dao.UserDao

        Class<?>[] interfaces = target.getClass().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            // interface com.luban.dao.UserDao
            System.out.println(anInterface);
        }

        // interface com.luban.dao.UserDao
        System.out.println(target.getClass().getInterfaces()[0]);

        Class<?> targetInf = target.getClass().getInterfaces()[0];
        Method[] methods = targetInf.getDeclaredMethods();
        for (Method method : methods) {
            // void
            String returnTypeName = method.getReturnType().getSimpleName();
            System.out.println(returnTypeName);
            // add
            String methodName = method.getName();
            System.out.println(methodName);
            // Sting.class String.class
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                String simpleName = parameterType.getSimpleName();
                // int int
                System.out.print(simpleName+" ");
            }
        }
    }

    @Test
    public void testsubstring() {
        String argsContent = "String i1,String i2,";

        // 19
        System.out.println(argsContent.lastIndexOf(","));

        if (argsContent.length() > 0) {

            //substring(int beginIndex, int endIndex) 不包括endIndex
            argsContent = argsContent.substring(0, argsContent.lastIndexOf(","));
        }

        // String i1,String i2
        System.out.println(argsContent);
    }

}
