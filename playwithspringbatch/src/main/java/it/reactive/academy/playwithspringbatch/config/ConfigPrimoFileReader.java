package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigPrimoFileReader {

    public static final String PRIMOSTEP_CHUNK_FILE_READER = "PRIMOSTEP_CHUNK_FILE_READER";
    public static final String PRIMOJOB_CHUNK_FILE_READER = "PRIMOJOB_CHUNK_FILE_READER";
    public static final int CHUNK_SIZE = 2;
    public static final String CUSTOM_ITEM_STREAM_READER = "CUSTOM_ITEM_STREAM_READER";
    public static final String ITEM_STREAM_WRITER_FILE = "ITEM_STREAM_WRITER_FILE";

    @Bean(PRIMOJOB_CHUNK_FILE_READER)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_FILE_READER) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_FILE_READER, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_FILE_READER)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(CUSTOM_ITEM_STREAM_READER)ItemStreamReader<String> reader
            , @Qualifier(ITEM_STREAM_WRITER_FILE) ItemStreamWriter<String> writer
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_FILE_READER, jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(item -> item.toUpperCase())
                .writer(writer)
                .build();
    }
}
