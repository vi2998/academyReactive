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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.*;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration

public class ConfigJobFlatFileItemWriterDelimitato {

    public static final String PRIMOSTEP_CHUNK_WRITER_DELIMITATO = "PRIMOSTEP_CHUNK_WRITER_DELIMITATO";
    public static final String PRIMOJOB_CHUNK_WRITER_DELIMITATO = "PRIMOJOB_CHUNK_WRITER_DELIMITATO";
    public static final String READER_JPA = "READER_JPA";
    public static final String WRITER_DELIMITATO = "WRITER_DELIMITATO";
    public static final String WRITER_POSIZIONALE = "WRITER_POSIZIONALE";
    public static final int CHUNK_SIZE = 5;
    public static final int PAGE_SIZE = 10;


    @Bean(PRIMOJOB_CHUNK_WRITER_DELIMITATO)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_WRITER_DELIMITATO) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_WRITER_DELIMITATO, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_WRITER_DELIMITATO)
    @JobScope
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(ConfigurazioniDatasourceApplicativa.ENTITYMANAGERFACTORY_APPLICATIVA) EntityManagerFactory entityManagerFactory
            , @Value("#{jobParameters['nome']}") String nome
    ) {

        // AGGREGATOR DELIMITATO
        DelimitedLineAggregator<Persona> lineAggregatorDelimitato = new DelimitedLineAggregator<>();
        lineAggregatorDelimitato.setDelimiter(";");
        BeanWrapperFieldExtractor<Persona> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"nome", "cognome", "eta"});
        lineAggregatorDelimitato.setFieldExtractor(fieldExtractor);

        //AGGREGATOR POSIZIONALE
        FormatterLineAggregator<Persona> lineaggregatorFixed = new FormatterLineAggregator<>();
        lineaggregatorFixed.setFormat("%-20s%-30s%-10s");
        BeanWrapperFieldExtractor<Persona> fieldExtractorFixed = new BeanWrapperFieldExtractor<>();
        fieldExtractorFixed.setNames(new String[]{"nome","cognome","eta"});
        lineaggregatorFixed.setFieldExtractor(fieldExtractorFixed);

        return new StepBuilder(PRIMOSTEP_CHUNK_WRITER_DELIMITATO, jobRepository)
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
                        new CompositeItemWriter<>(  // usato per fare più di un writer, separati da una virgola
                                chunk -> chunk.forEach(System.out::println),
                                new FlatFileItemWriterBuilder<Persona>()
                                        .name(WRITER_DELIMITATO)
                                        .resource(new FileSystemResource("file//outdelimitato.txt"))
                                        .lineAggregator(lineAggregatorDelimitato)
                                        .build()
                                , new FlatFileItemWriterBuilder<Persona>()
                                .name(WRITER_POSIZIONALE)
                                .resource(new FileSystemResource("file//outposizionale.txt"))

                                /*
                                // modo 1
                                .formatted()
                                    .format("%-10s%-20s%-10s") // il - allinea gli spazi a destra
                                    .names(new String[]{"nome","cognome","eta"})

                                 */
                                .lineAggregator(lineaggregatorFixed)
                                .build()
                        )
                )
                .build();
    }
}
