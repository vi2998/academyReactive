package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.reader.ReaderWithCP;
import it.reactive.academy.playwithspringbatch.dto.Persona;
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

public class ConfigJobWithCP {

    public static final String PRIMOSTEP_CHUNK_CP = "PRIMOSTEP_CHUNK_CP";
    public static final String PRIMOJOB_CHUNK_CP = "PRIMOJOB_CHUNK_CP";
    public static final String READER_CP = "READER_CP";
    public static final int CHUNK_GROUP_SIZE = 2;

    @Bean(PRIMOJOB_CHUNK_CP)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_CP) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_CP, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_CP)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
    ) {
        ReaderWithCP reader =  new ReaderWithCP(CHUNK_GROUP_SIZE);
        return new StepBuilder(PRIMOSTEP_CHUNK_CP, jobRepository)
                .<String, String>chunk(reader, transactionManager)
                .reader(reader)
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        System.out.println("------------------------------------------");
                        chunk.forEach(el -> {
                            if (el.startsWith("AN")){
                                System.out.println("Salvo l'anagrafica: " + el.substring(2));
                            } else if (el.startsWith("IN")) {
                                if (el.substring(2,3).equals("D")){
                                    System.out.println("Salvo l'indirizzo DOMICILIO: " + el.substring(3));
                                } else {
                                    System.out.println("Salvo l'indirizzo RESIDENZA: " + el.substring(3));

                                }
                            }
                        });
                    }
                })
                .build();
    }
}
