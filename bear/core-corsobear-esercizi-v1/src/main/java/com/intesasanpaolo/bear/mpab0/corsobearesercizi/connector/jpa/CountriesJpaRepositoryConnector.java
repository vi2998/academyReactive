package com.intesasanpaolo.bear.mpab0.corsobearesercizi.connector.jpa;

import com.intesasanpaolo.bear.connector.jpa.JPAConnector;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CountriesJpaRepositoryConnector extends JPAConnector<CountryModel,Long> {
}
