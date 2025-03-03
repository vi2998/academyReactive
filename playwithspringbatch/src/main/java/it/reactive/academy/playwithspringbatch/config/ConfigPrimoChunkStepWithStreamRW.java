package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigPrimoChunkStepWithStreamRW {

    public static final String PRIMOSTEP_CHUNK_WITH_STREAM_RW = "PRIMOSTEP_CHUNK_WITH_STREAM_RW";
    public static final String PRIMOJOB_CHUNK_WITH_STREAM_RW = "PRIMOJOB_CHUNK_WITH_STREAM_RW";
    public static final int CHUNK_SIZE = 2;
    public static final String ITEM_STREAM_READER = "ITEM_STREAM_READER";
    private static final String ITEM_STREAM_WRITER = "ITEM_STREAM_WRITER";

    @Bean(PRIMOJOB_CHUNK_WITH_STREAM_RW)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_WITH_STREAM_RW) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_WITH_STREAM_RW, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_WITH_STREAM_RW)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ITEM_STREAM_READER)ItemStreamReader<Double> reader
            , @Qualifier(ITEM_STREAM_WRITER) ItemStreamWriter<Double> writer
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_WITH_STREAM_RW, jobRepository)
                .<Double, Double>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean(ITEM_STREAM_READER)
    public ItemStreamReader<Double> itemStreamReader() {
        return new ItemStreamReader<Double>() {
            @Override
            public void open(ExecutionContext executionContext) throws ItemStreamException {
                ItemStreamReader.super.open(executionContext);
                System.out.println("------------------- OPEN READER -------------------");
            }

            @Override
            public void update(ExecutionContext executionContext) throws ItemStreamException {
                ItemStreamReader.super.update(executionContext);
                System.out.println("++++++++++++++++ UPDATE READER ++++++++++++++++");
            }

            @Override
            public void close() throws ItemStreamException {
                ItemStreamReader.super.close();
                System.out.println("****************** CLOSE READER ******************");
            }

            @Override
            public Double read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                double v = Math.random() * 10;
                if (v >9) return null;
                return v;
            }
        };
    }

    @Bean(ITEM_STREAM_WRITER)
    public ItemStreamWriter<Double> writer(){
        return new ItemStreamWriter<Double>() {
            @Override
            public void open(ExecutionContext executionContext) throws ItemStreamException {
                ItemStreamWriter.super.open(executionContext);
                System.out.println("------------------- OPEN WRITER -------------------");
            }

            @Override
            public void update(ExecutionContext executionContext) throws ItemStreamException {
                ItemStreamWriter.super.update(executionContext);
                System.out.println("++++++++++++++++ UPDATE WRITER ++++++++++++++++");
            }

            @Override
            public void close() throws ItemStreamException {
                ItemStreamWriter.super.close();
                System.out.println("****************** CLOSE WRITER ******************");
            }

            @Override
            public void write(Chunk<? extends Double> chunk) throws Exception {
                System.out.println("******************************************************************************");
                chunk.forEach(el -> System.out.println(el));
            }
        };
    }
}
