package it.reactive.academy.playwithspringbatch.config.reader;

import it.reactive.academy.playwithspringbatch.dto.Persona;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import static it.reactive.academy.playwithspringbatch.config.ConfigJobFlatFileFileReader.FLAT_ITEM_STREAM_READER;

@Component(FLAT_ITEM_STREAM_READER)
public class FlatFileReaderDelimitato extends FlatFileItemReader<Persona> {

    public FlatFileReaderDelimitato(){

        // setResource(new FileSystemResource("file/filefixed.txt")); // altro modo per recuperare il file

        setResource(new ClassPathResource("FileDelimitatoInClassPath.txt")); // deve essere in una cartella di maven
        setLinesToSkip(1); // skippo la linea di intestazione
        DefaultLineMapper<Persona> lineMapperDelimitato = new DefaultLineMapper<Persona>();
        LineTokenizer delimitatoTokenizer = new DelimitedLineTokenizer(";");
        lineMapperDelimitato.setLineTokenizer(delimitatoTokenizer);
        FieldSetMapper<Persona> fieldSetLineMapper = new FieldSetMapper<Persona>() {
            @Override
            public Persona mapFieldSet(FieldSet fieldSet) throws BindException {
                Persona persona= new Persona();
                persona.setNome(fieldSet.readString(0));
                persona.setCognome(fieldSet.readString(1));
                persona.setEta(fieldSet.readInt(2));
                return persona;
            }
        };
        lineMapperDelimitato.setFieldSetMapper(fieldSetLineMapper);

        setLineMapper(lineMapperDelimitato);


        /*
     LineMapper<String> lineMapper = new LineMapper<String>() {
         @Override
         public String mapLine(String line, int lineNumber) throws Exception {
             System.out.println(lineNumber);
            // return "..."; // così ritorno i puntini per ogni linea del file
             return line; // così ritorno le linee del file
         }
     };

        setLineMapper(new PassThroughLineMapper()); // PassThroughLineMapper viene utilizzata per restituire direttamente la riga letta dal file senza alcuna elaborazione o mappatura.

     */

    }
}
