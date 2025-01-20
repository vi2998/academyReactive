package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.connector;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountryRequestTransformer implements IRestRequestTransformer<Long, Void> {

    @Override
    public RestConnectorRequest<Void> transform(Long id, Object... args) {
        RestConnectorRequest<Void> restConnectorRequest = new RestConnectorRequest<>();
        HashMap<String, String> mapId = new HashMap<>();
        mapId.put("id", String.valueOf(id));
        restConnectorRequest.setParams(mapId);
        return restConnectorRequest;
    }
}
