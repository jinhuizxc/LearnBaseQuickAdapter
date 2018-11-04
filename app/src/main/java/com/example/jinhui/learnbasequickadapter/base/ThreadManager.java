package com.example.jinhui.learnbasequickadapter.base;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhusr on 2017/6/29.
 */

public class ThreadManager {

    private static ThreadPool mThreadPool;

    // 获取单例的线程池对象
    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadManager.class) {
                if (mThreadPool == null) {
                    int cpuNum = Runtime.getRuntime().availableProcessors();// 获取处理器数量
                    int threadNum = cpuNum * 2 + 1;// 根据cpu数量,计算出合理的线程并发数
                    System.out.println("cpu num:" + cpuNum);
                    mThreadPool = new ThreadPool(threadNum, threadNum, 0L);
                }
            }
        }
        return mThreadPool;
    }

    public static class ThreadPool {

        private ThreadPoolExecutor mExecutor;

        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;


        private ThreadPool(int corePoolSize, int maximumPoolSize,
                           long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }


        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }

            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize,// 核心线程数
                        maximumPoolSize, // 最大线程数
                        keepAliveTime, // 闲置线程存活时间
                        TimeUnit.MILLISECONDS,// 时间单位
                        new LinkedBlockingDeque<Runnable>(),// 线程队列
                        Executors.defaultThreadFactory(),// 线程工厂
                        new ThreadPoolExecutor.AbortPolicy()// 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略
                );
            }
            mExecutor.execute(runnable);
        }


        // 从线程队列中移除对象
        public void cancel(Runnable runnable) {
            if (mExecutor != null) {
                mExecutor.getQueue().remove(runnable);
            }
        }
    }
}
