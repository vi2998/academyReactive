package it.reactive.torneoDemoMongo.service;

import it.reactive.torneoDemoMongo.mapper.GiocatoreMapper;
import it.reactive.torneoDemoMongo.repository.dao.iGiocatoreDao;
import it.reactive.torneoDemoMongo.resource.GiocatoreResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GiocatoreService {

    @Autowired
    iGiocatoreDao iGiocatoreDao;

    @Autowired
    GiocatoreMapper giocatoreMapper;


    public GiocatoreResponse aggiornaAmmonizione(ObjectId idGiocatore)  {
        return giocatoreMapper.fromModelToResponse(iGiocatoreDao.aggiungiAmmonizioneGiocatore(idGiocatore));
    }
}
