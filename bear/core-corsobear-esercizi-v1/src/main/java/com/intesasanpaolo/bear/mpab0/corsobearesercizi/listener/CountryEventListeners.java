package com.intesasanpaolo.bear.mpab0.corsobearesercizi.listener;

import com.intesasanpaolo.bear.eventlistener.BaseEventListener;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.command.CountryCommandEventListener;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.dto.ProvaMessaggioDTO;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryEventListeners extends BaseEventListener {

    @Autowired
    BeanFactory beanFactory;

    @Value("${KAFKA_TOPIC_DEMO}")
    private String TOPIC;

    @Override
    public void onReceived(byte[] payload, Headers headers) {

        try {
            JsonDeserializer<ProvaMessaggioDTO> js = new JsonDeserializer<>(ProvaMessaggioDTO.class);
            ProvaMessaggioDTO pr = js.deserialize(TOPIC, headers, payload);
            System.out.println(pr.getMessaggio());
            String lingua = pr.getMessaggio();
            List<CountryModel> countryModelList = new ArrayList<>();
                   countryModelList = beanFactory.getBean(CountryCommandEventListener.class, lingua).execute();
            System.out.println("countryModelList = " + countryModelList);
        } catch (Exception e) {
            logger.info("ERRORE NELLA DESERIALIZZAZIONE DI {}", new String(payload));
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onReceivedStorage(byte[] payloadContent, Headers headers, Acknowledgment ack) {
        super.onReceivedStorage(payloadContent, headers, ack);
    }
}
