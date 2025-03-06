package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceApplicativa;
import it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceSpringBatch;
import it.reactive.academy.playwithspringbatch.dto.Persona;
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
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
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

public class ConfigJobJdbcItemWriter {

    public static final String PRIMOSTEP_CHUNK_WRITER_JDBC = "PRIMOSTEP_CHUNK_WRITER_JDBC";
    public static final String PRIMOJOB_CHUNK_WRITER_JDBC = "PRIMOJOB_CHUNK_WRITER_JDBC";
    public static final String READER_JPA = "READER_JPA";
    public static final int CHUNK_SIZE = 5;
    public static final int PAGE_SIZE = 10;

    @Bean(PRIMOJOB_CHUNK_WRITER_JDBC)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_WRITER_JDBC) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_WRITER_JDBC, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_WRITER_JDBC)
    @JobScope
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasourceApplicativa.DATA_SOURCE_APPLICATIVA) DataSource dataSource
            , @Qualifier(ConfigurazioniDatasourceApplicativa.ENTITYMANAGERFACTORY_APPLICATIVA) EntityManagerFactory entityManagerFactory
            , @Qualifier(ConfigurazioniDatasourceApplicativa.JDBC_TEMPLATE_APPLICATIVO) NamedParameterJdbcTemplate jdbcTemplate
            , @Value("#{jobParameters['nome']}") String nome
    ) {

        ItemPreparedStatementSetter<Persona> itemPrepareStatementSetterPersona = new ItemPreparedStatementSetter<Persona>() {
            @Override
            public void setValues(Persona item, PreparedStatement ps) throws SQLException {
                ps.setString(1,item.getNome() + " " + item.getCognome());
                ps.setInt(2,item.getEta());
            }
        };


        /*
        ItemSqlParameterSourceProvider<Persona> itemPrepareStatementSetter = new ItemSqlParameterSourceProvider<Persona>() {
            @Override
            public SqlParameterSource createSqlParameterSource(Persona item) {
                MapSqlParameterSource sqlParameterSource=new MapSqlParameterSource();
                sqlParameterSource.addValue("nome", item.getCognome());
                sqlParameterSource.addValue("eta", item.getEta());
                return sqlParameterSource;
            }
        };
        ItemSqlParameterSourceProvider<Persona> itemPrepareStatementSetter = new BeanPropertyItemSqlParameterSourceProvider<Persona>();
         */



        return new StepBuilder(PRIMOSTEP_CHUNK_WRITER_JDBC, jobRepository)
                .<PersonaModel, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(new JpaPagingItemReaderBuilder<PersonaModel>()
                        .name(READER_JPA)
                        .queryString("from PersonaModel where nome=:nome")
                        .pageSize(PAGE_SIZE)
                        .parameterValues(Collections.singletonMap("nome", nome))
                        .entityManagerFactory(entityManagerFactory)
                        .build()
                )
                .processor(new ItemProcessor<PersonaModel, Persona>() {
                    @Override
                    public Persona process(PersonaModel item) throws Exception {
                        Persona persona = new Persona();
                        persona.setCognome(item.getCognome());
                        persona.setNome(item.getNome());
                        persona.setEta(item.getEta());
                        return persona;
                    }
                })
                .writer(
                        new JdbcBatchItemWriterBuilder<Persona>()
                                .dataSource(dataSource)
                                .itemPreparedStatementSetter(itemPrepareStatementSetterPersona)
//                            .namedParametersJdbcTemplate(jdbcTemplate)
//                            .itemPreparedStatementSetter(itemPrepareStatementSetter2)
                                //.sql("insert into personacensita (nomecompleto, eta) values (:nome, :eta)")
                                .sql("insert into personacensita (nomecompleto, eta) values (?,?)")

                                .build()

                )
                .build();
    }
}