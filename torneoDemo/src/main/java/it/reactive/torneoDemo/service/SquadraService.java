package it.reactive.torneoDemo.service;


import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class SquadraService {

    @Autowired
    ISquadraDao iSquadraDao;

    public void rimuoviSquadra(int id) throws SQLException {
        iSquadraDao.rimuoviSquadra(id);
    }

    public SquadraModel aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO) throws SQLException {
       return iSquadraDao.aggiungiGiocatore(id, giocatoreDTO);
    }

    public SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) throws SQLException {
        return iSquadraDao.aggiungiTifoseria(id, tifoseriaDTO);
    }

    public void salvaSquadra(SquadraDTO squadraDTO){
        iSquadraDao.salvaSquadra(squadraDTO);
    }

    public void salvaSquadraDiGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO){
        iSquadraDao.salvaSquadraDiGiocatori(squadreDiGiocatoriDTO);
    }



}
