package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;

import java.sql.SQLException;
import java.util.List;

public interface ISquadraDao {
    SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException;
    SquadraModel salvaSquadraGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO) throws SQLException;
    SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) throws SQLException;
    SquadraModel aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO) throws SQLException;
    List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori);
    void rimuoviSquadra(int id) throws SQLException;
}