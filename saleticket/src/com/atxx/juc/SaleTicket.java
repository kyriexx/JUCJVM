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
