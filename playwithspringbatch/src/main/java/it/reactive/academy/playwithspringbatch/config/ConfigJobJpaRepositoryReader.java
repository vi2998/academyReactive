package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasource;
import it.reactive.academy.playwithspringbatch.config.dto.Persona;
import it.reactive.academy.playwithspringbatch.entity.PersonaModel;
import it.reactive.academy.playwithspringbatch.repository.PersonaRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ConfigJobJpaRepositoryReader {

    public static final String PRIMOSTEP_CHUNK_READER_WITH_JPA_REPOSITORY = "PRIMOSTEP_CHUNK_READER_WITH_JPA_REPOSITORY";
    public static final String PRIMOJOB_CHUNK_READER_WITH_JPA_REPOSITORY = "PRIMOJOB_CHUNK_READER_WITH_JPA_REPOSITORY";
    public static final String READER_JPAREPOSITORY = "READER_JPAREPOSITORY";
    public static final int CHUNK_SIZE = 2;

    @Bean(PRIMOJOB_CHUNK_READER_WITH_JPA_REPOSITORY)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_READER_WITH_JPA_REPOSITORY) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_READER_WITH_JPA_REPOSITORY, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_READER_WITH_JPA_REPOSITORY)
    @JobScope // lo step viene creato solo quando viene creato il job perchè jobParameters non era stato ancora creato
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasource.DATA_SOURCE_CLIENTI) DataSource dataSource
            , @Value("#{jobParameters['nomi']}") List<String> nomi

            , @Value("#{jobParameters['nome']}") String nome    // espressione SpEL (spring expression language)
            , PersonaRepository personaRepository
    ) {
        System.out.println("nomi = " + nomi);

//        PreparedStatementSetter pss = new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, "MARIO");
//            }
//        };    // lo cancello perchè uso la lambda

        Map<String, Sort.Direction> sortMap= new HashMap<>();
        sortMap.put("cognome", Sort.Direction.DESC);
        return new StepBuilder(PRIMOSTEP_CHUNK_READER_WITH_JPA_REPOSITORY, jobRepository)
                .<PersonaModel, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(new RepositoryItemReaderBuilder<PersonaModel>()
                        .name(READER_JPAREPOSITORY)
                        .sorts(sortMap)
                        .repository(personaRepository)
                        .arguments(Collections.singletonList(nome))
                        .methodName("findByNome")
                        .build()
                )
                .processor(new ItemProcessor<PersonaModel, Persona>() {
                    @Override
                    public Persona process(PersonaModel item) throws Exception {
                        Persona persona = new Persona();
                        persona.setNome(item.getNome());
                        persona.setCognome(item.getCognome());
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
