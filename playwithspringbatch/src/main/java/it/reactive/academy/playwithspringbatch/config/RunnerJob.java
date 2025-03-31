package it.reactive.academy.playwithspringbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Profile("!test")
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
        List<String> listaNomi = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
           listaNomi.add(args[i]);
        }
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(), true) // addString se voglio insererire una stringa
                // ma se volessi usare un'altra classe uso addJobParameter con ("nomevarialibe", variabile,classe)
                .addJobParameter("nomi", listaNomi, List.class)
                .addString("nome", listaNomi.get(0))

                .toJobParameters();
        Job job = (Job) beanFactory.getBean(nomeJob);
        jobLauncher.run(job, jobParameters);
    }
}
