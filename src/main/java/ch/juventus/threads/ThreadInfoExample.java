package ch.juventus.threads;

import java.util.ArrayList;
import java.util.List;

public class ThreadInfoExample {

    public static void main(String[] args) {
        System.out.printf("Hello %s\n", Thread.currentThread().getName());

        Runnable r = new ThreadInfo();

        for (int i=0; i<10; i++) {
            Thread t1 = new Thread(r);
            System.out.printf("%s: %s\n", t1.getName(), t1.getState());
            t1.start();
            System.out.printf("%s: %s\n", t1.getName(), t1.getState());
        }

    }

    private static class ThreadInfo implements Runnable {
        @Override
        public void run() {
            System.out.printf("Hello %s\n", java.lang.Thread.currentThread().getName());
        }
    }
}
