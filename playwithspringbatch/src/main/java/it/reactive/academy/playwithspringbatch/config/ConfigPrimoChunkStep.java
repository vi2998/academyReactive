package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ConfigPrimoChunkStep {

    public static final String PRIMOSTEP_CHUNK = "PRIMOSTEP_CHUNK";
    public static final String PRIMOJOB_CHUNK = "PRIMOJOB_CHUNK";
    public static final int CHUNK_SIZE = 10;
    public static final String ITEM_READER = "ITEM_READER";



    @Bean(PRIMOJOB_CHUNK)
    public Job creaPrimoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK, jobRepository)
                .start(step)
                .build();
    }

    @Bean(PRIMOSTEP_CHUNK)
    public Step creaPrimoStepChunk(JobRepository jobRepository, PlatformTransactionManager transactionManager
            ,@Qualifier(ITEM_READER) ItemReader<Double> doubleItemReader // posso farlo anche senza questo e creare il reader nello step
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK, jobRepository)
                .<Integer, Integer>chunk(CHUNK_SIZE, transactionManager)
                .reader(new ItemReader<Integer>() { // potrei fare una lambda, oppure un bean e richiamrlo sopra
                    @Override
                    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return 0;
                    }
                })
                .writer(new ItemStreamWriter<Integer>() {
                    @Override
                    public void write(Chunk<? extends Integer> chunk) throws Exception {
                        System.out.println("***********************");
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }

    @Bean(ITEM_READER)
    public ItemReader<Double> itemReaderDouble(){
        return new ItemReader<Double>() {
            @Override
            public Double read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return 0.0;
            }
        };
    }
}
