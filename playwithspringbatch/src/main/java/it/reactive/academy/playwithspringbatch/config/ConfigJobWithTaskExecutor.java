package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.EccezioneDimensioneErrata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigJobWithTaskExecutor {

    public static final String PRIMOSTEP_CHUNK_TASKEXECUTOR = "PRIMOSTEP_CHUNK_TASKEXECUTOR";
    public static final String PRIMOJOB_CHUNK_TASKEXECUTOR = "PRIMOJOB_CHUNK_TASKEXECUTOR";
    public static final int CHUNK_SIZE = 5;
    public static final String READER_TASKEXECUTOR = "READER_TASKEXECUTOR";
    public static final String THREAD_TASK_EXECUTOR = "THREAD_TASK_EXECUTOR" ;
    public static final String LISTENER_JOB_TASKEXECUTOR = "LISTENER_JOB_TASKEXECUTOR";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(PRIMOJOB_CHUNK_TASKEXECUTOR)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_TASKEXECUTOR) Step step
            , @Qualifier(LISTENER_JOB_TASKEXECUTOR) JobExecutionListener listener
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_TASKEXECUTOR, jobRepository)
                .start(step)
                .listener(listener)
                .build();
    }

    @Bean(LISTENER_JOB_TASKEXECUTOR)
    public JobExecutionListener listener(
            @Qualifier(THREAD_TASK_EXECUTOR) ThreadPoolTaskExecutor taskExecutor
    ){
        return new JobExecutionListener() {
            @Override
            public void afterJob(JobExecution jobExecution) {
                JobExecutionListener.super.afterJob(jobExecution);
                System.out.println("FINE!!!!!!!!!!");
                taskExecutor.destroy();
            }
        };
    }

    @Bean(THREAD_TASK_EXECUTOR)
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10); // numero di thread di base che vngono mantenuti nel pool anche se sono inattivi
        taskExecutor.setMaxPoolSize(20); /* numero max di thread che possono essere creati nel pool.
                                            Se il num. di thread è = a  maxPoolSize e ci sono più task da eseguire,
                                            questi task verranno messi in coda fino a quando un thread non sarà disponibile */
        return new ThreadPoolTaskExecutor();
    }

    @Bean(PRIMOSTEP_CHUNK_TASKEXECUTOR)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(THREAD_TASK_EXECUTOR) TaskExecutor taskExecutor
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_TASKEXECUTOR, jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(new FlatFileItemReaderBuilder<String>()
                        .name(READER_TASKEXECUTOR)
                        .resource(new FileSystemResource("file/file4taskexecutor.txt"))
                        .lineMapper(new PassThroughLineMapper())
                        .build()
                )
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        chunk.forEach(el -> logger.info("elemento: {}",el));
                    }
                })
                /*.taskExecutor(new SimpleAsyncTaskExecutor()) // venogno fatti in multithread: non vengono stampati i ordine
                     sono stati creati 4 thread, 1 per ciascun chunk e le elaborazione sono state fatte parallelamente */

                //.taskExecutor(taskExecutor) non conviene usare quello nel contesto di spring. conviene usare il simple
                .build();
    }
}
