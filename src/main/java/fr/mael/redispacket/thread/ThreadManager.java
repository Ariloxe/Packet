package fr.mael.redispacket.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private ExecutorService executorService;

    public ThreadManager(String name, int threads) {
        this.executorService = Executors.newFixedThreadPool(threads, new ThreadFactoryBuilder().setNameFormat(name).build());
    }

    public void execute(Runnable runnable) {
        executorService.execute(() -> {
            try {
                runnable.run();
            } finally {

            }
        });
    }
}
