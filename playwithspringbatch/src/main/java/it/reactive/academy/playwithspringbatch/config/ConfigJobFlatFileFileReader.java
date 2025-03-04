package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.dto.Persona;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigJobFlatFileFileReader {

    public static final String PRIMOSTEP_CHUNK_FILE_READER_FLAT = "PRIMOSTEP_CHUNK_FILE_READER_FLAT";
    public static final String PRIMOJOB_CHUNK_FILE_READER_FLAT = "PRIMOJOB_CHUNK_FILE_READER_FLAT";
    public static final int CHUNK_SIZE = 2;
    public static final String FLAT_ITEM_STREAM_READER = "FLAT_ITEM_STREAM_READER";

    @Bean(PRIMOJOB_CHUNK_FILE_READER_FLAT)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_FILE_READER_FLAT) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_FILE_READER_FLAT, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_FILE_READER_FLAT)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(FLAT_ITEM_STREAM_READER)ItemStreamReader<Persona> reader
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_FILE_READER_FLAT, jobRepository)
                .<Persona, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .writer(new ItemWriter<Persona>() {
                    @Override
                    public void write(Chunk<? extends Persona> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }
}
