package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;

import java.util.List;

public interface ISquadraDao {
    SquadraModel aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO);
    SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO);
    SquadraModel salvaSquadra(SquadraDTO squadraDTO);
    SquadraModel salvaSquadraDiGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO);
    SquadraModel rimuoviSquadra(int id);
    List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori);
}