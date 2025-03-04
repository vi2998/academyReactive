package it.reactive.academy.playwithspringbatch.config.reader;

import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static it.reactive.academy.playwithspringbatch.config.ConfigPrimoFileReader.CUSTOM_ITEM_STREAM_READER;

@Component(CUSTOM_ITEM_STREAM_READER)
public class CustomFileReader implements ItemStreamReader<String>{

    private BufferedReader reader;

    @Override
            public void open(ExecutionContext executionContext) throws ItemStreamException {
                ItemStreamReader.super.open(executionContext);
                System.out.println("------------------- OPEN READER -------------------");
                try{
                    String filePath = "file/file.txt";
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                } catch (IOException i) {
                    throw new RuntimeException();
                }
            }

            @Override
            public void close() throws ItemStreamException {
                ItemStreamReader.super.close();
                System.out.println("****************** CLOSE READER ******************");
                if (reader!= null){
                    try {
                        reader.close();
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
               return reader.readLine();

            }
        };