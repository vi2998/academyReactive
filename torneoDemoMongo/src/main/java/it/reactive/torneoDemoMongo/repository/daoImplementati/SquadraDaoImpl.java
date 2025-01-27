package it.reactive.torneoDemoMongo.repository.daoImplementati;

import it.reactive.torneoDemoMongo.dto.GiocatoreDTO;
import it.reactive.torneoDemoMongo.dto.SquadraDTO;
import it.reactive.torneoDemoMongo.dto.TifoseriaDTO;
import it.reactive.torneoDemoMongo.model.SquadraModel;
import it.reactive.torneoDemoMongo.repository.dao.iSquadraDao;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class SquadraDaoImpl implements iSquadraDao {

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) {

        SquadraModel squadraModel = null;

        return null;
    }

    @Override
    public SquadraModel aggiungiTifoseria(ObjectId id, TifoseriaDTO tifoseriaDTO) {
        return null;
    }

    @Override
    public SquadraModel aggiungiGiocatore(ObjectId id, GiocatoreDTO giocatoreDTO) {
        return null;
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) {
        return Collections.emptyList();
    }

    @Override
    public void rimuoviSquadra(ObjectId id) {

    }
}
