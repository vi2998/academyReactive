package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.dto.Persona;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigJobJdbcReader {

    public static final String PRIMOSTEP_CHUNK_JDBC_READER = "PRIMOSTEP_CHUNK_JDBC_READER";
    public static final String PRIMOJOB_CHUNK_JDBC_READER = "PRIMOJOB_CHUNK_JDBC_READER";
    public static final int CHUNK_SIZE = 2;
    public static final String JDBC_READER = "JDBC_READER";

    @Bean(PRIMOJOB_CHUNK_JDBC_READER)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_JDBC_READER) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_JDBC_READER, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_JDBC_READER)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(JDBC_READER)ItemStreamReader<Persona> reader
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_JDBC_READER, jobRepository)
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
