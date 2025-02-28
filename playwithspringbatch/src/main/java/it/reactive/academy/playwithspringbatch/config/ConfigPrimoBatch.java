package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// @EnableBatchProcessing si metteva fino a batch 2
public class ConfigPrimoBatch {
    public static final String PRIMOSTEP = "PRIMOSTEP";
    public static final String SECONDOSTEP = "SECONDOSTEP";

    public static final String PRIMOJOB = "PRIMOJOB";
    public static final String SECONDOJOB = "SECONDOJOB";
    public static final String TERZOJOB = "TERZOJOB";

//    @Autowired
//    JobRepository jobRepository;

//    @Autowired
//    PlatformTransactionManager transactionManager;

    @Bean(PRIMOJOB)
    public Job creaPrimoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP) Step step
            , @Qualifier(SECONDOSTEP) Step secondoStep) {
        return new JobBuilder(PRIMOJOB, jobRepository)
                .start(step)
                .next(secondoStep)
                .build();
    }

    // se ho 2 job devo dire quale job voglio lanciare.
    // lo faccio dall'application properties

    @Bean(SECONDOJOB)
    public Job creaSecondoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP) Step step
            , @Qualifier(SECONDOSTEP) Step secondoStep) {
        return new JobBuilder(SECONDOJOB, jobRepository)
                .start(step)
                .next(secondoStep)
                .build();
    }

    @Bean(TERZOJOB)
    public Job creaTerzoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP) Step step
            , @Qualifier(SECONDOSTEP) Step secondoStep) {
        return new JobBuilder(TERZOJOB, jobRepository)
                .start(step)
                .next(secondoStep)
                .build();
    }

    @Bean(PRIMOSTEP)
    public Step creaPrimoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(PRIMOSTEP, jobRepository)
                .tasklet(creaTasklet(), transactionManager)
                .build();
    }

    // se non do il nome al bean userà il nome del metodo
    @Bean(SECONDOSTEP)
    public Step creaSecondoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(SECONDOSTEP, jobRepository)
                .tasklet(creaTasklet(), transactionManager)
                .build();
    }

    public Tasklet creaTasklet() {
        return (contribution, chunkContext) -> {
            String nome = chunkContext.getStepContext() .getJobParameters().get("nome").toString();
            System.out.println("CIAOOOOO " + nome);
            return RepeatStatus.FINISHED;   // Quando finisce bisogna mettere questo
        };
    }
}
