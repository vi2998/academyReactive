package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceSpringBatch;
import it.reactive.academy.playwithspringbatch.dto.Persona;
import it.reactive.academy.playwithspringbatch.entity.PersonaModel;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration

public class ConfigJobWithPartitioner {

    public static final String MASTER_STEP = "MASTER_STEP";
    public static final String PRIMOJOB_CHUNK_PART = "PRIMOJOB_CHUNK_PART";
    public static final String PARTITIONER = "PARTITIONER";
    public static final String SLAVE_STEP = "SLAVE_STEP";
    public static final String READER_FILE_COMUNI = "READER_FILE_COMUNI";
    public static final String PROCESSOR_FILE_COMUNI = "PROCESSOR_FILE_COMUNI";
    public static final int CHUNK_SIZE = 5;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean(PRIMOJOB_CHUNK_PART)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(MASTER_STEP) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_PART, jobRepository)
                .start(step)
                .build();
    }

    @Bean(MASTER_STEP)
    public Step creaMasterStep(JobRepository jobRepository
            , @Qualifier(PARTITIONER) Partitioner partitioner
            , @Qualifier(SLAVE_STEP) Step slaveStep
    ) {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setThreadNamePrefix("COMUNE-");
        return new StepBuilder(MASTER_STEP, jobRepository)
                .partitioner(PARTITIONER, partitioner)
                .step(slaveStep)
                .taskExecutor(simpleAsyncTaskExecutor)
                .build();
    }

    @JobScope
    @Bean(PARTITIONER)
    public Partitioner partitioner(
            @Value("#{jobParameters['nomi']}") List<String> nomi
    ) {
        return new Partitioner() {
            @Override
            public Map<String, ExecutionContext> partition(int gridSize) {
                Map<String, ExecutionContext> map = new HashMap<>(nomi.size());
                for (int i = 0; i < nomi.size(); i++) {
                    ExecutionContext context = new ExecutionContext();
                    context.putString("nomefile", nomi.get(i));
                    map.put("nomefile:" + nomi.get(i), context);
                }
                return map;
            }
        };
    }

    @Bean(SLAVE_STEP)
    public Step slavestep(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(READER_FILE_COMUNI) ItemStreamReader<String> reader
            , @Qualifier(PROCESSOR_FILE_COMUNI) ItemProcessor<String, String> processor
    ) {
        return new StepBuilder(SLAVE_STEP, jobRepository)
                .<String, String> chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(new ItemStreamWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        for (String string : chunk) {
                            System.out.println("string = " + string);
                        }
                    }
                })
                .build();
    }

    @StepScope
    @Bean(READER_FILE_COMUNI)
    public ItemStreamReader<String> reader(
            @Value("#{stepExecutionContext['nomefile']}") String nomefile
    ){
        return new FlatFileItemReaderBuilder<String>()
                .name("READER_COMUNI")
                .resource(new FileSystemResource("file/" + nomefile + ".txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @StepScope
    @Bean(PROCESSOR_FILE_COMUNI)
    public ItemProcessor<String, String> processor(
            @Value("#{stepExecutionContext['nomefile']}") String nomefile
    ){
        return new ItemProcessor<String, String>() {
            @Override
            public String process(String item) throws Exception {
                log.info("Il comune è: {}", nomefile );
                return nomefile + " -> " + item;
            }
        };
    }
}
