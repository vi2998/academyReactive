package it.reactive.academy.playwithspringbatch.config;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

//@Configuration
public class H2BatchConfiguration {
    @Bean(name = "dataSource")
    @BatchDataSource
    public DataSource H2Datasource(){
        return new EmbeddedDatabaseBuilder()
                // questi file esistono nel jar di spring batch core
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql") // drop tabelle
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql") // crea tabelle.
                // per postgres possiamo copiare questo script per creare le tabelle
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
