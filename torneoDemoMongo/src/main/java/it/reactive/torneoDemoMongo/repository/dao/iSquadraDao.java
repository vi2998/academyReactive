package it.reactive.torneoDemoMongo.repository.dao;

import it.reactive.torneoDemoMongo.dto.GiocatoreDTO;
import it.reactive.torneoDemoMongo.dto.SquadraDTO;
import it.reactive.torneoDemoMongo.dto.TifoseriaDTO;
import it.reactive.torneoDemoMongo.model.SquadraModel;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.util.List;

public interface iSquadraDao {

    SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException;
    SquadraModel aggiungiTifoseria(ObjectId id, TifoseriaDTO tifoseriaDTO) ;
    SquadraModel aggiungiGiocatore(ObjectId id, GiocatoreDTO giocatoreDTO) ;
    List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori); /* TODO: nell'implementazione JPA
    TODO: -------------fare la ricerca con una Query (non native) nel caso di completo e NamedQuery nel caso di non completo. */
    void rimuoviSquadra(ObjectId id);
}