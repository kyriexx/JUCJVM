package com.luban.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kyrie
 * @create 2020-01-02 8:42
 */

public class TicketRobert {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {for (int i = 0; i < 200; i++) {
            ticket.getNumber();
        }
        },"窗口1").start();

        new Thread(() -> {for (int i = 0; i < 200; i++) {
            ticket.getNumber();
        }
        },"窗口2").start();

        new Thread(() -> {for (int i = 0; i < 200; i++) ticket.getNumber();},"窗口3").start();

        new Thread(() -> {for (int i = 0; i < 200; i++) ticket.getNumber();},"窗口4").start();
    }
}

class Ticket {
    private int number = 1;
    private int max = 500;

    private Lock lock = new ReentrantLock();

    public /*synchronized*/ void getNumber() {
        lock.lock();
        try {
            if (number <= max) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number++));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
