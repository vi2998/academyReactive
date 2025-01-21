package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector;

import com.intesasanpaolo.bear.event.BaseEventConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.dto.ProvaMessaggioDTO;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConnector extends BaseEventConnector<ProvaMessaggioDTO, Boolean, String, Void> {

}