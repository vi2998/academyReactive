package it.reactive.torneoDemoMongo.repository.daoImplementati;

import it.reactive.torneoDemoMongo.model.GiocatoreModel;
import it.reactive.torneoDemoMongo.repository.dao.iGiocatoreDao;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class GiocatoreDaoImpl implements iGiocatoreDao {
    @Override
    public GiocatoreModel aggiungiAmmonizioneGiocatore(ObjectId idGiocatore) {
        return null;
    }
}
