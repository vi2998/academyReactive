package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ConfigBatchWithJobListener {

    public static final String PRIMOSTEP_WITH_JOB_LISTENER = "PRIMOSTEP_WITH_JOB_LISTENER";
    public static final String JOB_WITH_JOB_LISTENER = "JOB_WITH_JOB_LISTENER";


    @Bean(JOB_WITH_JOB_LISTENER)
    public Job creaPrimoJob(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_WITH_JOB_LISTENER) Step step
    ) {
        return new JobBuilder(JOB_WITH_JOB_LISTENER, jobRepository)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        JobExecutionListener.super.beforeJob(jobExecution);
                        nomi = (List<String>) jobExecution.getJobParameters().getParameter("nomi").getValue();
                        System.out.println("*********************");
                        System.out.println("*********************");
                        System.out.println("******** BEFORE ********");
                        System.out.println("nomi: " + nomi);
                        System.out.println("*********************");
                        System.out.println("*********************");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        JobExecutionListener.super.afterJob(jobExecution);
                        System.out.println("---------------------------");
                        System.out.println("---------------------------");
                        System.out.println("----------- AFTER -----------");
                        System.out.println("Conta: " + nomiLungo5Caratteri);
                        System.out.println("---------------------------");
                        System.out.println("---------------------------");
                    }
                })
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_WITH_JOB_LISTENER)
    public Step creaPrimoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(PRIMOSTEP_WITH_JOB_LISTENER, jobRepository)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        StepExecutionListener.super.beforeStep(stepExecution);
                        System.out.println("---------------- BEFORE STEP ----------------");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("---------------- AFTER STEP ----------------");
                        if (false){
                            return ExitStatus.FAILED;
                        }
                        return StepExecutionListener.super.afterStep(stepExecution);
                    }
                })
                .tasklet(creaTasklet(), transactionManager)
                .build();
    }

    int nomiLungo5Caratteri = 0;
    List<String> nomi;

    public Tasklet creaTasklet() {
        return (contribution, chunkContext) -> {
            for (String nome : nomi) {
                if (nome.length() == 5){
                    nomiLungo5Caratteri++;
                }
                System.out.println("CIAO: " + nome);
            }
            return RepeatStatus.FINISHED;   // Quando finisce bisogna mettere questo
        };
    }
}