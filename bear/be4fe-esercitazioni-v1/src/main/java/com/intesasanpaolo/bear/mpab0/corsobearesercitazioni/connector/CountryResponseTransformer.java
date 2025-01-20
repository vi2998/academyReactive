package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.resource.CountryResource;
import org.springframework.stereotype.Service;

@Service
public class CountryResponseTransformer implements IRestResponseTransformer<CountryResource, String> {

    @Override
    public String transform(RestConnectorResponse<CountryResource> restConnectorResponse) {
        CountryResource countryResource = restConnectorResponse.getResponse().getBody();

        return String.valueOf(countryResource.getChiave());
    }
}
