package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.service;

import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryRestConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.KafkaEventConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.transformer.KafkaEventRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.transformer.KafkaEventResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.dto.ProvaMessaggioDTO;
import com.intesasanpaolo.bear.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends BaseService {

    @Autowired
    CountryRestConnector connector;

    @Autowired
    CountryRequestTransformer countryRequestTransformer;

    @Autowired
    CountryResponseTransformer countryResponseTransformer;


    public String getCountry(Long id){
        return connector.call(id, countryRequestTransformer, countryResponseTransformer);
    }

    @Autowired
    KafkaEventConnector kafkaEventConnector;

    @Autowired
    private KafkaEventRequestTransformer kafkaEventRequestTransformer;

    @Autowired
    private KafkaEventResponseTransformer kafkaEventResponseTransformer;

    public void produci (ProvaMessaggioDTO provaMessaggioDTO){
        kafkaEventConnector.call(provaMessaggioDTO, kafkaEventRequestTransformer, kafkaEventResponseTransformer);
    }

}
