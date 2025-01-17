package com.intesasanpaolo.bear.mpab0.corsobearesercizi.service;

import com.intesasanpaolo.bear.connector.jdbc.JDBCQueryType;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.jdbc.GetCountriesJdbcConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.resource.CountryResource;
import com.intesasanpaolo.bear.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountryService extends BaseService {

    @Autowired
    GetCountriesJdbcConnector getCountriesJdbcConnector;

    @Autowired
    private GetCountriesJDBCRequestTransformer getCountriesJDBCRequestTransformer;

    @Autowired
    private GetCountriesJDBCResponseTransformer getCountriesJDBCResponseTransformer;


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

    public List<CountryModel> getJdbc() {
        List<CountryModel> response =
                getCountriesJdbcConnector.call("SELECT * FROM countries",
                        getCountriesJDBCRequestTransformer,
                        getCountriesJDBCResponseTransformer,
                        JDBCQueryType.FIND);
        return response;
    }
}