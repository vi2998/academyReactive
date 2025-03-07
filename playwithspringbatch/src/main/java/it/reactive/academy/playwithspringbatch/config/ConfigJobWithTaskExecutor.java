package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.EccezioneDimensioneErrata;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
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
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class ConfigJobWithStepFaultTollerant {

    public static final String PRIMOSTEP_CHUNK_TOLLERANT = "PRIMOSTEP_CHUNK_TOLLERANT";
    public static final String PRIMOJOB_CHUNK_TOLLERANT = "PRIMOJOB_CHUNK_TOLLERANT";
    public static final int CHUNK_SIZE = 5;
    public static final String SKIP_LISTENER = "SKIP_LISTENER";

    @Bean(PRIMOJOB_CHUNK_TOLLERANT)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_TOLLERANT) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_TOLLERANT, jobRepository)
                .start(step)
                .build();
    }

    // per salvare le informazioni dei record che sono andati in errore
    @Bean(SKIP_LISTENER)
    public SkipListener skipListener(){
        return new SkipListener() {
            @Override
            public void onSkipInRead(Throwable t) {
                SkipListener.super.onSkipInRead(t);
            }

            @Override
            public void onSkipInWrite(Object item, Throwable t) {
                SkipListener.super.onSkipInWrite(item, t);
            }

            @Override
            public void onSkipInProcess(Object item, Throwable t) {
                SkipListener.super.onSkipInProcess(item, t);
                System.out.println("************************" + item);
            }
        };
    }


    @Bean(PRIMOSTEP_CHUNK_TOLLERANT)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(SKIP_LISTENER) SkipListener skipListener
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_TOLLERANT, jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(new FlatFileItemReaderBuilder<String>()
                        .name("READER_TOLLERANT")
                        .resource(new FileSystemResource("file/nuovofile.txt"))
                        .lineMapper(new PassThroughLineMapper())
                        .build()
                )
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        if (item.equals("aaaaaaaa")) throw new RuntimeException();
                        if (item.length() == 5) throw new EccezioneDimensioneErrata();
                        return item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })

                .faultTolerant().skipLimit(4) // al 4 errore fallisce
                .skip(EccezioneDimensioneErrata.class)
                .listener(skipListener)
                .build();
    }
}
