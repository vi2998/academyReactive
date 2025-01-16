package com.intesasanpaolo.bear.mpab0.corsobearesercizi.service;

import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountryService extends BaseService {

    public List<CountryModel> getCountries() {
        List<CountryModel> countryModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CountryModel countryModel = new CountryModel(i, "nome_" + i + "-lingua_" + i + " -continente_" + i);
            countryModelList.add(countryModel);
        }
        return countryModelList;
    }

    public List<CountryModel> getCountriesParam(int id, String info) {
        List<CountryModel> countryModelList = getCountries();

        countryModelList.add(new CountryModel(id, info)); // alla lista presa aggiungo un countryModel
        return countryModelList;

    }
}