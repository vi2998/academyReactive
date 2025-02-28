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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class ConfigPrimoBatch {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String PRIMOSTEP = "PRIMOSTEP";
    public static final String PRIMOJOB = "PRIMOJOB";

    @Bean(PRIMOJOB)
    public Job creaPrimoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP) Step step) {
        return new JobBuilder(PRIMOJOB, jobRepository)
                .start(step)
                .build();
    }

    @Bean(PRIMOSTEP)
    public Step creaPrimoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(PRIMOSTEP, jobRepository)
                .tasklet(creaTasklet(), transactionManager)
                .build();
    }

    public Tasklet creaTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Inizio cancellazione dati...");
            jdbcTemplate.execute("DROP TABLE IF EXISTS squadra_torneo CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS tifoseria CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS giocatore CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS torneo CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS squadra CASCADE");

            System.out.println("Database cancellato con successo!");
            return RepeatStatus.FINISHED;
        };
    }
}
