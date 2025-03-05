package it.reactive.academy.playwithspringbatch.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class ConfigurazioniDatasourceApplicativa {

    public static final String DATA_SOURCE_APPLICATIVA = "dataSourceApplicativa";
    public static final String ENTITYMANAGERFACTORY_APPLICATIVA = "ENTITYMANAGERFACTORY_APPLICATIVA";
    public static final String JDBC_TEMPLATE_APPLICATIVO="JDBC_TEMPLATE_APPLICATIVO";

    @Bean(name = DATA_SOURCE_APPLICATIVA)
    @ConfigurationProperties(prefix = "spring.datasource-applicativa")
    public DataSource applicativaDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = ENTITYMANAGERFACTORY_APPLICATIVA)
    public LocalContainerEntityManagerFactoryBean clientiEntityManager(
            @Qualifier(DATA_SOURCE_APPLICATIVA) DataSource datasource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setPackagesToScan("it.reactive.academy.playwithspringbatch.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(JDBC_TEMPLATE_APPLICATIVO)
    public NamedParameterJdbcTemplate creaJdbcTemplate(@Qualifier(DATA_SOURCE_APPLICATIVA) DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
