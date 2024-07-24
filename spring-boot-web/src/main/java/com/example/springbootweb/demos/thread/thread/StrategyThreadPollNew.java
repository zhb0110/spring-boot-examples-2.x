package com.example.springbootweb.demos.thread.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zhb
 * @create 2023/10/13 0013 17:50
 * 充放电策略线程池类
 */
@Configuration
public class StrategyThreadPollNew {

    @Bean("strategyThreadPollManager")
    public ThreadPoolExecutor strategyThreadPollManager() {
        // 并发集合类
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(2000);
        // 创建指定动态范围的线程池：ThreadPoolExecutor
        // 最小10个同时执行，最大1000个，当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁，单位，
        // 具体流程：
        // 总线程启停每个试验，根据线程的状态来控制启停单独的小线程，
        // 小线程执行策略
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(5, 100, 10, TimeUnit.SECONDS, queue);
        return executor;
    }

}
