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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class RunnerJob implements CommandLineRunner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    BeanFactory beanFactory;

    @Override
    public void run(String... args) throws Exception {
        String nomeJob = args[0];
        System.out.println("nomeJob = " + nomeJob);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(), true)
                .toJobParameters();
        Job job = (Job) beanFactory.getBean(nomeJob);
        jobLauncher.run(job, jobParameters);
    }
}
