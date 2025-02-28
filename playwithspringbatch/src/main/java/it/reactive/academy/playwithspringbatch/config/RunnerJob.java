package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RunnerJob implements CommandLineRunner {
// le classi che implementano CommandLineRunner appena si avvia l'app vengono eseguite in automatico

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    BeanFactory beanFactory;

//    @Qualifier(ConfigPrimoBatch.PRIMOJOB)
//    Job job;


    @Override
    public void run(String... args) throws Exception {
        String nomeJob = args[0];
        System.out.println("nomeJob = " + nomeJob);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", "NEW_UUID_BIS", true)
                .addString("nome", args[1])
                .toJobParameters();

        Job job = (Job) beanFactory.getBean(nomeJob);
        jobLauncher.run(job, jobParameters);
    }
}
