package it.reactive.academy.playwithspringbatch.config.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

import java.io.*;

import static it.reactive.academy.playwithspringbatch.config.ConfigPrimoFileReader.ITEM_STREAM_WRITER_FILE;

@Component(ITEM_STREAM_WRITER_FILE)
public class CustomFileWriter implements ItemStreamWriter<String> {
    BufferedWriter bufferedWriter;

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        chunk.forEach(el -> {
            try {
                bufferedWriter.write(el);
                bufferedWriter.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ItemStreamWriter.super.open(executionContext);
        String filePath = "file/out.txt";
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() throws ItemStreamException {
        ItemStreamWriter.super.close();
        if (bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
