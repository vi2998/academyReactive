package com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.jdbc;

import com.intesasanpaolo.bear.connector.jdbc.JDBCQueryType;
import com.intesasanpaolo.bear.connector.jdbc.JdbcConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCRequestTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.transformer.GetCountriesJDBCResponseTransformer;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.resource.CountryResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCountriesJdbcConnector extends JdbcConnector<String, List<CountryModel>, Void, CountryModel> {

}
