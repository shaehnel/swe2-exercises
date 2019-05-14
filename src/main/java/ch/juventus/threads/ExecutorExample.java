package ch.juventus.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorExample {

    public static void main(String[] args) {
        singleThreadExecutorExample();

        threadPoolExecutorExample();

        scheduledExecutorExample();
//
//        Callable<String> callable = () -> "hello";
//        Future<String> result = executor.submit(callable);
//        System.out.println(result.isDone());
//        executor.shutdownNow();
    }

    private static void singleThreadExecutorExample() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i=0; i<20; i++){
            final int n = i;
            executor.execute(() -> System.out.printf("hello single %d %s\n", n, Thread.currentThread().getName()));
        }
        executor.shutdown();
    }

    private static void threadPoolExecutorExample() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = new ArrayList<>();
        for (int i=0; i<20; i++){
            final int n = i;
            Future<String> f = executor.submit(() -> String.format("hello fixed %d %s", n, Thread.currentThread().getName()));
            futures.add(f);
        }
            futures.forEach(future ->
            {
                try {
                    System.out.println(future.get(1, TimeUnit.SECONDS));
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            });
        executor.shutdown();
    }

    private static void scheduledExecutorExample() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        for (int i=0; i<20; i++){
            final int n = i;
            executor.schedule(() -> System.out.printf("hello scheduled %d %s\n", n, Thread.currentThread().getName()),
                2, TimeUnit.SECONDS);
        }
        executor.shutdown();
    }
}
