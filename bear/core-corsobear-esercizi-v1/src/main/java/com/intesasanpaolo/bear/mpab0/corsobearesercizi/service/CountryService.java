package com.intesasanpaolo.bear.mpab0.corsobearesercizi.service;

import com.intesasanpaolo.bear.connector.jdbc.JDBCQueryType;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.jdbc.GetCountriesJdbcConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.jpa.CountriesJpaRepositoryConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.resource.CountryResource;
import com.intesasanpaolo.bear.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CountryService extends BaseService {

    @Autowired
    CountriesJpaRepositoryConnector countriesJpaRepositoryConnector;

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

    public Optional<CountryModel> getJpa(Long id) {
        Optional<CountryModel> countryModelOptional = countriesJpaRepositoryConnector.findById(id);
        return countryModelOptional;
    }

    public List<CountryModel> getLanguage(String lingua) {
        String query = "select * from countries where info like '%" + lingua + "%'";
        return getCountriesJdbcConnector.call(query, getCountriesJDBCRequestTransformer, getCountriesJDBCResponseTransformer);
    }
}