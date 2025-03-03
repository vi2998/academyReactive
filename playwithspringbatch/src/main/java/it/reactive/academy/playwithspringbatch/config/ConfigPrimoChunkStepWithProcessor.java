package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigPrimoChunkStepWithProcessor {

    public static final String PRIMOSTEP_CHUNK_WITH_PROCESSOR = "PRIMOSTEP_CHUNK_WITH_PROCESSOR";
    public static final String PRIMOJOB_CHUNK_WITH_PROCESSOR = "PRIMOJOB_CHUNK_WITH_PROCESSOR";
    public static final String CUSTOM_PROCESSOR = "CUSTOM_PROCESSOR";
    public static final int CHUNK_SIZE = 2;

    @Bean(PRIMOJOB_CHUNK_WITH_PROCESSOR)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_WITH_PROCESSOR) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_WITH_PROCESSOR, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_WITH_PROCESSOR)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(CUSTOM_PROCESSOR) ItemProcessor<Double, String> processor
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_WITH_PROCESSOR, jobRepository)
                .<Double, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(new ItemReader<Double>() {
                    @Override
                    public Double read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        double v = Math.random() * 10;
                        if (v > 9) return null;
                        return v;
                    }
                })
                .processor(processor)
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        System.out.println("**************************");
                        chunk.forEach(el -> System.out.println(el));
                    }
                })
                .build();
    }

    @Bean(CUSTOM_PROCESSOR)
    public ItemProcessor<Double, String> processor() {
        return new ItemProcessor<Double, String>() {
            @Override
            public String process(Double item) throws Exception {
                return "#" + String.valueOf(item * -1) + "#";
            }
        };
    }

}
