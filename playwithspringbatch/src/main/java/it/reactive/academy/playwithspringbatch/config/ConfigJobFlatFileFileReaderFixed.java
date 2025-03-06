package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.dto.Persona;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

@Configuration

public class ConfigJobFlatFileFileReaderFixed {

    public static final String PRIMOSTEP_CHUNK_FILE_READER_FLAT_FIXED = "PRIMOSTEP_CHUNK_FILE_READER_FLAT_FIXED";
    public static final String PRIMOJOB_CHUNK_FILE_READER_FLAT_FIXED = "PRIMOJOB_CHUNK_FILE_READER_FLAT_FIXED";
    public static final int CHUNK_SIZE = 2;
    public static final String FLAT_ITEM_STREAM_READER_FIXED = "FLAT_ITEM_STREAM_READER_FIXED";

    @Bean(PRIMOJOB_CHUNK_FILE_READER_FLAT_FIXED)
    public Job creaPrimoJobChunk(JobRepository jobRepository
            , @Qualifier(PRIMOSTEP_CHUNK_FILE_READER_FLAT_FIXED) Step step
    ) {
        return new JobBuilder(PRIMOJOB_CHUNK_FILE_READER_FLAT_FIXED, jobRepository)
                .start(step)
                .build();
    }


    @Bean(PRIMOSTEP_CHUNK_FILE_READER_FLAT_FIXED)
    public Step creaPrimoStepChunk(JobRepository jobRepository
            , PlatformTransactionManager transactionManager
            , @Qualifier(FLAT_ITEM_STREAM_READER_FIXED)ItemStreamReader<Persona> reader
    ) {
        return new StepBuilder(PRIMOSTEP_CHUNK_FILE_READER_FLAT_FIXED, jobRepository)
                .<Persona, Persona>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .writer(new ItemWriter<Persona>() {
                    @Override
                    public void write(Chunk<? extends Persona> chunk) throws Exception {
                        chunk.forEach(System.out::println);
                    }
                })
                .build();
    }

    @Bean(FLAT_ITEM_STREAM_READER_FIXED)
    public FlatFileItemReader<Persona> getPersonaFromFlatFile(){
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(new Range(1,10),new Range(11,30),new Range(31));
        FieldSetMapper<Persona> fsm = new FieldSetMapper<Persona>() {
            @Override
            public Persona mapFieldSet(FieldSet fieldSet) throws BindException {
                Persona persona= new Persona();
                persona.setNome(fieldSet.readString(0));
                persona.setCognome(fieldSet.readString(1));
                persona.setEta(fieldSet.readInt(2));
                return persona;
            }
        };


        return new FlatFileItemReaderBuilder<Persona>()
                .name(FLAT_ITEM_STREAM_READER_FIXED) // devo passargli il nome del bean
                .lineTokenizer(tokenizer)
                .resource(new FileSystemResource("file/filefixed.txt"))
                .fieldSetMapper(fsm)
                .build();
    }
}
