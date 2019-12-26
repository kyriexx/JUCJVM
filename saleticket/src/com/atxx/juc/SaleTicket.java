package com.atxx.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kyrie
 * @create 2019-12-26 10:48
 * <p>
 * 题目：三个售票员         卖出          30张票
 * 笔记：如何编写企业级的多线程代码
 * 固定的编程套路+模板是什么？
 * <p>
 * 1 在高内聚低耦合的前提下，线程        操作      资源类
 * <p>
 * 1.1 一言不合，先创建一个资源类
 */

class Ticket {
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t 还剩下： " + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicket {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); }, "窗口1").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); }, "窗口2").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); }, "窗口3").start();
    }
}

/*
Thread 类的有关法方法
 void start(): 启动线程，并执行对象的run()方法
 run(): 线程在被调度时执行的操作
 String getName(): 返回线程的名称
 void setName(String name):设置该线程名称
 static Thread currentThread(): 返回当前线程。在Thread子类中就是this，通常用于主线程和Runnable实现类
 static void yield()： ： 线程让步
暂停当前正在执行的线程，把执行机会让给优先级相同或更高的线程
若队列中没有同优先级的线程，忽略此方法
 join() ：当某个程序执行流中调用其他线程的 join() 方法时，调用线程将
被阻塞，直到 join() 方法加入的 join 线程执行完为止
低优先级的线程也可以获得执行
 static void sleep(long millis) ：(指定时间:毫秒)
令当前活动线程在指定时间段内放弃对CPU控制,使其他线程有机会被执行,时间到后
重排队。
抛出InterruptedException异常
 stop(): 强制线程生命期结束，不推荐使用
 boolean isAlive()： ： 返回boolean，判断线程是否还活着


Synchronized 的使用方法
1. 同步代码 块 ：
synchronized ( 对象){
//  需要被同步的代码；
}
2. synchronized 还可以放在方法声明中，表示 整个 方法为 同步方法 。
例如：
public synchronized void show (String name){
….
}

 synchronized 的锁是什么 ？
 任意对象都可以作为同步锁。所有对象都自动含有单一的锁（监视器）。
 同步方法的锁：静态方法（类名.class）、非静态方法（this）
 同步代码块：自己指定，很多时候也是指定为this或类名.class

释放 锁的操作
 当前线程的同步方法、同步代码块执行结束。
 当前线程在同步代码块、同步方法中遇到break、return终止了该代码块、
该方法的继续执行。
 当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导
致异常结束。
 当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线
程暂停，并释放锁。

不会 释放锁的操作
线程执行同步代码块或同步方法时，程序调用Thread.sleep()、
Thread.yield()方法暂停当前线程的执行
线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程
挂起，该线程不会释放锁（同步监视器）。
应尽量避免使用suspend()和resume()来控制线程

  线程 的 通信
 wait() 与notify()和 notifyAll()
wait()：令当前线程挂起并放弃CPU、同步资源并等待，使别的线程可访问并修改共享资源，而当
前线程排队等候其他线程调用notify()或notifyAll()方法唤醒，唤醒后等待重新获得对监视器的所有
权后才能继续执行。
notify()：唤醒正在排队等待同步资源的线程中优先级最高者结束等待
notifyAll ()：唤醒正在排队等待资源的所有线程结束等待.
 这三个方法只有在synchronized方法或synchronized代码块中才能使用，否则会报
java.lang.IllegalMonitorStateException异常。
 因为这三个方法必须有锁对象调用，而任意对象都可以作为synchronized的同步锁，
因此这三个方法只能在Object类中声明。

*/
