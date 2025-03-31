package it.reactive.academy.playwithspringbatch;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

    @Configuration
    @Profile("test")
    public class ConfigurazioniDatasourceSpringBatch {

        public static final String DATA_SOURCE = "dataSource";

        @Primary
        @Bean(name = DATA_SOURCE)
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

        @Primary
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                @Qualifier(DATA_SOURCE) DataSource dataSource
        ) {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("it.reactive.academy.playwithspringbatch.entity");
            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);

            return em;
        }


    }
