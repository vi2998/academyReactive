package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ConfigBatchWithError {

    public static final String PRIMOSTEP_WITH_ERROR = "PRIMOSTEP_WITH_ERROR";
    public static final String PRIMOSTEP_WITHOUT_ERROR = "PRIMOSTEP_WITHOUT_ERROR";
    public static final String JOB_WITH_ERROR = "JOB_WITH_ERROR";



    @Bean(JOB_WITH_ERROR)
    public Job creaPrimoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_WITHOUT_ERROR) Step stepOk
            , @Qualifier(PRIMOSTEP_WITH_ERROR) Step step
    ){
        return new JobBuilder(JOB_WITH_ERROR, jobRepository)
                .start(stepOk)
                .next(step)
                .build();
    }


    @Bean(PRIMOSTEP_WITH_ERROR)
    public Step creaPrimoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(PRIMOSTEP_WITH_ERROR, jobRepository)
                .tasklet(creaTasklet(), transactionManager)
                .build();
    }

    @Bean(PRIMOSTEP_WITHOUT_ERROR)
    public Step creaPrimoStepOk(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(PRIMOSTEP_WITHOUT_ERROR, jobRepository)
                .tasklet(creaTaskletOk(), transactionManager)
                .build();
    }


    public Tasklet creaTasklet() {
        return (contribution, chunkContext) -> {
            String nome = chunkContext.getStepContext().getJobParameters().get("nome").toString();
            if (nome.equals("PIPPO")){
                throw new RuntimeException("Nome non ammesso");
            }
            System.out.println("CIAOOOOO " + nome);
            return RepeatStatus.FINISHED;   // Quando finisce bisogna mettere questo
        };
    }

    public Tasklet creaTaskletOk() {
        return (contribution, chunkContext) -> {
            System.out.println("*********** PRIMO STEP***********");
            return RepeatStatus.FINISHED;   // Quando finisce bisogna mettere questo
        };
    }
}