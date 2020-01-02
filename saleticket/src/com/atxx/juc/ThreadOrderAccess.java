package com.atxx.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kyrie
 * @create 2019-12-27 12:16
 * <p>
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......来10轮
 * <p>
 * <p>
 * 1   高聚低合前提下，线程操作资源类
 * 2   判断/干活/通知
 * 3   多线程的交互中，必须要防止多线程的虚假唤醒（也即判断只能用while，不能用if）
 * 4   标志位
 */

// 资源类
class ShareResource {

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // 判断
            while (number != 1) {
                condition1.await();
            }

            // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            // 判断
            while (number != 2) {
                condition2.await();
            }

            // 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            // 判断
            while (number != 3) {
                condition3.await();
            }

            // 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }

            // 通知
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class ThreadOrderAccess {

    public static void main(String[] args) {

        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "C").start();

        //new Thread(() -> {for (int i = 0; i < 10; i++) shareResource.print15();},"D").start();
    }
}
/*
 从JDK 5.0开始，Java提供了更强大的线程同步机制——通过显式定义同
步锁对象来实现同步。同步锁使用Lock对象充当。
 java.util.concurrent.locks.Lock接口是控制多个线程对共享资源进行访问的
工具。锁提供了对共享资源的独占访问，每次只能有一个线程对Lock对象
加锁，线程开始访问共享资源之前应先获得Lock对象。
 ReentrantLock 类实现了 Lock ，它拥有与 synchronized 相同的并发性和
内存语义，在实现线程安全的控制中，比较常用的是ReentrantLock，可以显式加锁、释放锁。

*/
