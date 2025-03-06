package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceApplicativa;
import it.reactive.academy.playwithspringbatch.dto.Persona;
import it.reactive.academy.playwithspringbatch.entity.PersonaModel;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration

public class ConfigJobJpaReader {

    public static final String PRIMOSTEP_CHUNK_READER_WITH_JPA = "PRIMOSTEP_CHUNK_READER_WITH_JPA";
    public static final String PRIMOJOB_CHUNK_READER_WITH_JPA = "PRIMOJOB_CHUNK_READER_WITH_JPA";
    public static final String READER_JPA = "READER_JPA";
    public static final int CHUNK_SIZE = 5;
    public static final int PAGE_SIZE = 10;

    @Bean(PRIMOJOB_CHUNK_READER_WITH_JPA)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_READER_WITH_JPA) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_READER_WITH_JPA, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_READER_WITH_JPA)
    @JobScope
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasourceApplicativa.ENTITYMANAGERFACTORY_APPLICATIVA) EntityManagerFactory entityManagerFactory
            , @Value("#{jobParameters['nome']}") String nome
    ) {
        /*
        JpaNativeQueryProvider<PersonaModel> jqp = new JpaNativeQueryProvider<PersonaModel>();
        jqp.setSqlQuery("select * from persona where nome  = :nome");
        jqp.setEntityClass(PersonaModel.class);

         */

        return new StepBuilder(PRIMOSTEP_CHUNK_READER_WITH_JPA, jobRepository)
                .<PersonaModel, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(new JpaPagingItemReaderBuilder<PersonaModel>()
                        .name(READER_JPA)
                        //.queryProvider(jqp)
                        .queryString("from PersonaModel where nome=:nome") //jpql query
                        .pageSize(PAGE_SIZE)
                        .parameterValues(Collections.singletonMap("nome", nome))
                        .entityManagerFactory(entityManagerFactory)
                        .build()
                )
                .processor(new ItemProcessor<PersonaModel, Persona>() {
                    @Override
                    public Persona process(PersonaModel item) throws Exception {
                        Persona persona=new Persona();
                        persona.setCognome(item.getCognome());
                        persona.setNome(item.getNome());
                        persona.setEta(item.getEta());
                        return persona;
                    }
                })
                .writer(new ItemWriter<Persona>() {
                    @Override
                    public void write(Chunk<? extends Persona> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }
}
