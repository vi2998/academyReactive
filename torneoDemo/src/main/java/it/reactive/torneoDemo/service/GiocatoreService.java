package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class GiocatoreService {

    @Autowired
    IGiocatoreDao iGiocatoreDao;

    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        return iGiocatoreDao.aggiornaAmmonizione(idGiocatore);
    }
}
