package it.reactive.academy.playwithspringbatch.config;

import it.reactive.academy.playwithspringbatch.dto.Persona;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static it.reactive.academy.playwithspringbatch.config.ConfigJobFlatFileFileReader.FLAT_ITEM_STREAM_READER;
import static it.reactive.academy.playwithspringbatch.config.ConfigJobFlatFileFileReader.PRIMOJOB_CHUNK_FILE_READER_FLAT;
import static it.reactive.academy.playwithspringbatch.config.ConfigJobJpaItemWriter.PRIMOJOB_CHUNK_WRITER_JPA;
import static it.reactive.academy.playwithspringbatch.config.ConfigJobJpaItemWriter.PRIMOSTEP_CHUNK_WRITER_JPA;
import static it.reactive.academy.playwithspringbatch.config.ConfigPrimoBatch.PRIMOJOB;
import static it.reactive.academy.playwithspringbatch.config.ConfigPrimoChunkStepWithProcessor.CUSTOM_PROCESSOR;
import static it.reactive.academy.playwithspringbatch.config.ConfigPrimoFileReader.ITEM_STREAM_WRITER_FILE;
import static it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasourceApplicativa.DATA_SOURCE_APPLICATIVA;
import static org.assertj.core.api.Assertions.contentOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class ConfigPrimoBatchTest {

    @Autowired
    @Qualifier(PRIMOJOB)
    Job job;

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier(PRIMOJOB_CHUNK_WRITER_JPA)
    private Job jobJpaWriter;

    @Autowired
    @Qualifier(PRIMOJOB_CHUNK_FILE_READER_FLAT)
    private Job jobFile;

    @Autowired
    @Qualifier(DATA_SOURCE_APPLICATIVA)
    private DataSource dataSource;

    @Autowired
    @Qualifier(FLAT_ITEM_STREAM_READER)
    private ItemStreamReader<Persona> itemReader;

    @Autowired
    @Qualifier(ITEM_STREAM_WRITER_FILE)
    private ItemStreamWriter<String> itemWriter;

    @Autowired
    @Qualifier(CUSTOM_PROCESSOR)
    ItemProcessor<Double, String> itemProcessor;

    @Test
    void creaPrimoJob() throws Exception {
        jobLauncherTestUtils.setJob(job);
        JobParameters jobParameters = new JobParametersBuilder()
                //.addString("UUID", UUID.randomUUID().toString(),true)
                .addJobParameter("nomi", new ArrayList(), List.class)
                .addString("nome", "Daniele")
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getStatus().equals(BatchStatus.COMPLETED));
    }

    @Test
    void ricreaPrimoJob() throws Exception {
        jobLauncherTestUtils.setJob(job);
        JobParameters jobParameters = new JobParametersBuilder()
                //.addString("UUID", UUID.randomUUID().toString(),true)
                .addJobParameter("nomi", new ArrayList(), List.class)
                .addString("nome", "Daniele")
                .toJobParameters();
        assertThrows(JobInstanceAlreadyCompleteException.class, () -> {
            jobLauncherTestUtils.launchJob(jobParameters);
        });
    }

    @Test
    void creaJobJpaWriter() throws Exception {
        Statement statement = dataSource.getConnection().createStatement();
        statement.executeUpdate("delete from personacensita");
        jobLauncherTestUtils.setJob(jobJpaWriter);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(),true)
                .addJobParameter("nomi", new ArrayList(), List.class)
                .addString("nome", "CLAUDIO")
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getStatus().equals(BatchStatus.COMPLETED));
        ResultSet rs = statement.executeQuery("select * from personacensita where eta = 8");
        int contaRecord=0;
        while (rs.next()){
            assertEquals("claudio gialli",rs.getString("nomecompleto"));
            contaRecord++;
        }
        assertEquals(1, contaRecord);
    }

    @Test
    public void testStep() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(),true)
                .addJobParameter("nomi", new ArrayList(), List.class)
                .addString("nome", "CLAUDIO")
                .toJobParameters();
        jobLauncherTestUtils.setJob(jobJpaWriter);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(PRIMOSTEP_CHUNK_WRITER_JPA,jobParameters);
        Collection actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        Assertions.assertThat(actualStepExecutions.size()).isEqualTo(1);
        Assertions.assertThat(actualJobExitStatus.getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());

        StepExecution stepExecution = (StepExecution) actualStepExecutions.stream().toList().get(0);
        Assertions.assertThat(stepExecution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());
        Assertions.assertThat(stepExecution.getReadCount()).isEqualTo(2);
        Assertions.assertThat(stepExecution.getFilterCount()).isEqualTo(0);
        Assertions.assertThat(stepExecution.getWriteCount()).isEqualTo(2);

    }

    @Test
    public void testItemReaderFile() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(),true)
                .toJobParameters();
        //jobLauncherTestUtils.setJob(jobFile);

        List<Persona> righe=new ArrayList<>();
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution(jobParameters);
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            Persona riga;
            itemReader.open(stepExecution.getExecutionContext());
            while ((riga = itemReader.read()) != null) {
                righe.add(riga);
            }
            itemReader.close();
            return null;
        });

        Assertions.assertThat(righe.size()).isEqualTo(2);
        Assertions.assertThat(righe.get(0).getCognome()).isEqualTo("ROSSI");
    }


    @Test
    public void testFlatFileItemWriter() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("UUID", UUID.randomUUID().toString(),true)
                .toJobParameters();
        StepExecution stepExecution = MetaDataInstanceFactory
                .createStepExecution(jobParameters);
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            itemWriter.open(stepExecution.getExecutionContext());
            Chunk<? extends String> chunk=new Chunk<>(Arrays.asList("Riga di prova"));
            itemWriter.write(chunk);
            itemWriter.close();
            return null;
        });

        String outputContent = contentOf(new File("file/out.txt"));
        Assertions.assertThat(outputContent).contains("Riga di prova");
    }

    @Test
    public void testProcessor() throws Exception {
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        Double valore = 12D;
        String stringa = StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            return itemProcessor.process(valore);
        });
        Assertions.assertThat(stringa).isEqualTo("#-12.0#");
    }

}