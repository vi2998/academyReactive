package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.config.dto.Persona;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ConfigJobJdbcReaderWithArgumentSetter {

    public static final String PRIMOSTEP_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER = "PRIMOSTEP_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER";
    public static final String PRIMOJOB_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER = "PRIMOJOB_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER";
    public static final String JDBC_ARGUMENT_SETTER = "JDBC_ARGUMENT_SETTER";
    public static final int CHUNK_SIZE = 2;

    @Bean(PRIMOJOB_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER)
    @JobScope // lo spet viene creato solo quando viene creato il job perchè jobParameters non era stato ancora creato
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasource.DATA_SOURCE_CLIENTI) DataSource dataSource
            , @Value("#{jobParameters['nome']}") String nome    // espressione SpEL (spring expression language)
    ) {

//        PreparedStatementSetter pss = new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, "MARIO");
//            }
//        };    // lo cancello perchè uso la lambda

        return new StepBuilder(PRIMOSTEP_CHUNK_JDBC_READER_WITH_ARGUMENT_SETTER, jobRepository)
                .<Persona, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(new JdbcCursorItemReaderBuilder<Persona>()
                        .name(JDBC_ARGUMENT_SETTER)
                        .sql("select * from persona where nome = ?")
                        .preparedStatementSetter(ps -> ps.setString(1, nome))
                        .rowMapper((rs, intero) -> {
                            Persona persona = new Persona();
                            persona.setCognome(rs.getString("cognome"));
                            persona.setNome(rs.getString("nome"));
                            persona.setEta(rs.getInt("eta"));
                            return persona;
                        })
                        .dataSource(dataSource)
                        .build()
                )
                .writer(new ItemWriter<Persona>() {
                    @Override
                    public void write(Chunk<? extends Persona> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }
}
