package it.reactive.academy.playwithspringbatch.config.reader;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;

import static it.reactive.academy.playwithspringbatch.config.ConfigJobWithCP.READER_CP;


public class ReaderWithCP implements ItemStreamReader<String>, CompletionPolicy {

    private final FlatFileItemReader<String> delegate;
    private final Integer numeroElementiChunkGroup;
    private String read;
    private int contaChunkGroup;

    public ReaderWithCP(Integer numeroElementiChunkGroup) {
        this.numeroElementiChunkGroup = numeroElementiChunkGroup;
        delegate = new FlatFileItemReaderBuilder<String>()
                .name(READER_CP)
                .resource(new FileSystemResource("file/tipirecord.txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ItemStreamReader.super.open(executionContext);
        delegate.open(executionContext);
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        read = delegate.read();
        if (read != null && read.startsWith("FR")) {
            contaChunkGroup++;
        }
        return read;
    }

    @Override
    public void close() throws ItemStreamException {
        ItemStreamReader.super.close();
        delegate.close();
    }

    @Override
    public boolean isComplete(RepeatContext context, RepeatStatus result) {
        if (read == null) {
            return true;

        }
        if (numeroElementiChunkGroup >= contaChunkGroup) {
            contaChunkGroup = 0;
            return true;
        }

        return false;
    }

    @Override
    public boolean isComplete(RepeatContext context) {
        return false;
    }

    @Override
    public RepeatContext start(RepeatContext parent) {
        return parent;
    }

    @Override
    public void update(RepeatContext context) {

    }
}
