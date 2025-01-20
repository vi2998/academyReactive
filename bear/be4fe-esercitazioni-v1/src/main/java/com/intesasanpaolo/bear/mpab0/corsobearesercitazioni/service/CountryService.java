package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.service;

import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector.CountryRestConnector;
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

}
