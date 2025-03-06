package it.reactive.academy.playwithspringbatch.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ConfigurazioneJPaWriter {
    public final static String JPAWRITER_ENTITYMANAGERFACTORY = "JPAWRITER_ENTITYMANAGERFACTORY";
    public final static String JPAWRITER_DATASOURCE = "JPAWRITER_DATASOURCE";
    public final static String JPAWRITER_TRANSACTIONMANAGER = "JPAWRITER_TRANSACTIONMANAGER";


    @Bean(name = JPAWRITER_ENTITYMANAGERFACTORY)
    public LocalContainerEntityManagerFactoryBean clientiEntityManager(
            @Qualifier(JPAWRITER_DATASOURCE) DataSource datasource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPackagesToScan("it.reactive.academy.playwithspringbatch.entity");

        Properties hibernateProperties = new Properties();
        //hibernate.jdbc.batch_size: Questa proprietà determina il numero massimo di istruzioni SQL che Hibernate raggruppa in un batch per l'esecuzione. Un valore più grande può migliorare le prestazioni riducendo il numero di round trip al database.
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "1000000");
        //hibernate.jdbc.fetch_size: Questa proprietà determina il numero di righe che dovrebbero essere recuperate dal database in una singola iterazione. Un valore più grande può ridurre il numero di round trip al database.
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", "100");
        //hibernate.order_inserts: Quando è impostato su "true", Hibernate ordina le istruzioni di inserimento in modo che siano raggruppate per entità, il che può ottimizzare l'uso del batching JDBC.
        hibernateProperties.setProperty("hibernate.order_inserts", "false");
        //hibernate.order_updates: Quando è impostato su "true", Hibernate ordina le istruzioni di aggiornamento in modo che siano raggruppate per entità, il che può ottimizzare l'uso del batching JDBC.
        hibernateProperties.setProperty("hibernate.order_updates", "false");
        //hibernate.batch_versioned_data: Quando è impostato su "true", Hibernate include le istruzioni di aggiornamento versionate in un batch.
        hibernateProperties.setProperty("hibernate.batch_versioned_data", "false");
        em.setJpaProperties(hibernateProperties);


        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }


    @Bean(name = JPAWRITER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource-applicativa")
    public DataSource applicativaDatasource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(JPAWRITER_TRANSACTIONMANAGER)
    PlatformTransactionManager creaTM(@Qualifier(JPAWRITER_ENTITYMANAGERFACTORY) EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
