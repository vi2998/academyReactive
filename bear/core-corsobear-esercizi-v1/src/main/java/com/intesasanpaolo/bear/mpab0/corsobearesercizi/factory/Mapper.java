package com.intesasanpaolo.bear.mpab0.corsobearesercizi.factory;

import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.resource.CountryResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public List<CountryResource> countryModelsToResources(List<CountryModel> countryModels) {
        List<CountryResource> countryResources = new ArrayList<>();
        for (CountryModel countryModel : countryModels) {
            CountryResource countryResource = new CountryResource();
            String[] info = countryModel.getInfo().split("-");
            countryResource.setName(info[0]);
            countryResource.setLanguage(info[1]);
            countryResource.setContinent(info[2]);
            countryResource.setChiave(countryModel.getId());
            countryResources.add(countryResource);
        }
        return countryResources;
    }

    public CountryResource countryModelsToResource(CountryModel countryModel) {
        CountryResource countryResource = new CountryResource();
        String[] info = countryModel.getInfo().split("-");
        countryResource.setName(info[0]);
        countryResource.setLanguage(info[1]);
        countryResource.setContinent(info[2]);
        countryResource.setChiave(countryModel.getId());
        return countryResource;
    }
}

