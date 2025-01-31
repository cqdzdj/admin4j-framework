package com.admin4j.framework.ttl.configuration;

import com.admin4j.framework.ttl.props.ThreadPoolProperties;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author andanyang
 * @since 2022/11/4 16:00
 */
//TODO 参考 TaskExecutionAutoConfiguration 写一个
@AutoConfigureBefore(TaskExecutionAutoConfiguration.class)
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class TtlTaskExecutorAutoConfiguration {
    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    /**
     * @return
     */
    @Bean
    @Primary
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        /*
           rejection-policy：当pool已经达到max size的时候，如何处理新任务
           CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        //System.out.println("ThreadPoolTaskExecutor = " + threadPoolProperties.getThreadNamePrefix());
        return TtlExecutors.getTtlExecutor(executor);
    }
}
