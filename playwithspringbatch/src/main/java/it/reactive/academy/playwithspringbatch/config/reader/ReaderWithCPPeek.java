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
import static it.reactive.academy.playwithspringbatch.config.ConfigJobWithCPPeek.READER_CP_PEEK;

public class ReaderWithCPPeek implements ItemStreamReader<String>, CompletionPolicy, PeekableItemReader<String> {

    private final FlatFileItemReader<String> delegate;
    private final Integer numeroElementiChunkGroup;
    private int contaChunckGroup;
    private String nextItem;


    public ReaderWithCPPeek(Integer numeroElementiChunkGroup) {
        this.numeroElementiChunkGroup = numeroElementiChunkGroup;
        delegate = new FlatFileItemReaderBuilder<String>()
                .name(READER_CP_PEEK)
                .resource(new FileSystemResource("file/tipirecord_senza_fr.txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String read;
        if (nextItem != null){
         read = nextItem;
         nextItem = null;
        } else {
            read = delegate.read();
        }
        return read;
    }

    @Override
    public String peek() throws Exception, UnexpectedInputException, ParseException {
        nextItem = read();
        if (nextItem != null && nextItem.startsWith("AN")){
            contaChunckGroup++;
        }
        return nextItem;
    }

    @Override
    public boolean isComplete(RepeatContext context, RepeatStatus result) {
        try {
            peek();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (nextItem == null){
            return true;
        }
        if (numeroElementiChunkGroup <= contaChunckGroup){
            contaChunckGroup = 0;
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

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ItemStreamReader.super.open(executionContext);
        delegate.open(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        ItemStreamReader.super.close();
        delegate.close();
    }
}
