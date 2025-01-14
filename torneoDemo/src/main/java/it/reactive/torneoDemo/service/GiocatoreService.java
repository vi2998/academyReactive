package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.mapper.GiocatoreMapper;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import it.reactive.torneoDemo.resource.GiocatoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class GiocatoreService {

    @Autowired
    IGiocatoreDao iGiocatoreDao;

    @Autowired
    GiocatoreMapper giocatoreMapper;

    public GiocatoreResponse aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        return giocatoreMapper.fromModelToResponse(iGiocatoreDao.aggiornaAmmonizione(idGiocatore));
    }
}
