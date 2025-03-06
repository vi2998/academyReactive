package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioneJPaWriter;
import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceApplicativa;
import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceSpringBatch;
import it.reactive.academy.playwithspringbatch.dto.Persona;
import it.reactive.academy.playwithspringbatch.entity.PersonaCensita;
import it.reactive.academy.playwithspringbatch.entity.PersonaModel;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;

@Configuration

public class ConfigJobJpaItemWriter {

    public static final String PRIMOSTEP_CHUNK_WRITER_JPA = "PRIMOSTEP_CHUNK_WRITER_JPA";
    public static final String PRIMOJOB_CHUNK_WRITER_JPA = "PRIMOJOB_CHUNK_WRITER_JPA";
    public static final String READER_JPA = "READER_JPA";
    public static final int CHUNK_SIZE = 5;
    public static final int PAGE_SIZE = 10;

    @Bean(PRIMOJOB_CHUNK_WRITER_JPA)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_WRITER_JPA) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_WRITER_JPA, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_WRITER_JPA)
    @JobScope
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , @Qualifier(ConfigurazioneJPaWriter.JPAWRITER_TRANSACTIONMANAGER) PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasourceApplicativa.DATA_SOURCE_APPLICATIVA) DataSource dataSource
            , @Qualifier(ConfigurazioniDatasourceApplicativa.ENTITYMANAGERFACTORY_APPLICATIVA) EntityManagerFactory entityManagerFactory
            , @Qualifier(ConfigurazioneJPaWriter.JPAWRITER_ENTITYMANAGERFACTORY) EntityManagerFactory emf
            , @Value("#{jobParameters['nome']}") String nome
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_WRITER_JPA, jobRepository)
                .<PersonaModel, PersonaCensita>chunk(CHUNK_SIZE, transactionManager)
                .reader(new JpaPagingItemReaderBuilder<PersonaModel>()
                        .name(READER_JPA)
                        .queryString("from PersonaModel where nome=:nome")
                        .pageSize(PAGE_SIZE)
                        .parameterValues(Collections.singletonMap("nome", nome))
                        .entityManagerFactory(entityManagerFactory)
                        .build()
                )
                .processor(new ItemProcessor<PersonaModel, PersonaCensita>() {
                    @Override
                    public PersonaCensita process(PersonaModel item) throws Exception {
                        PersonaCensita persona = new PersonaCensita();
                        persona.setNomecompleto(item.getNome().toLowerCase() + " " + item.getCognome().toLowerCase() );
                        persona.setEta(item.getEta());
                        return persona;
                    }
                })
                .writer(
                        new JpaItemWriterBuilder<PersonaCensita>()
                                .entityManagerFactory(emf)  // USERA IL BATCH UPDATE
                                .usePersist(true)   // forzare uso di persist altrimenti usa merge
                                .build()
                )

                .build();
    }
}
