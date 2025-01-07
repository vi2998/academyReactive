package it.reactive.torneoDemo.service;


import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SquadraService {

    @Autowired
    ISquadraDao iSquadraDao;

    public void rimuoviSquadra(int id){
        iSquadraDao.rimuoviSquadra(id);
    }

    public void aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO){
        iSquadraDao.aggiungiGiocatore(id, giocatoreDTO);
    }

    public void aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) {
        iSquadraDao.aggiungiTifoseria(id, tifoseriaDTO);
    }

    public void salvaSquadra(SquadraDTO squadraDTO){
        iSquadraDao.salvaSquadra(squadraDTO);
    }

    public void salvaSquadraDiGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO){
        iSquadraDao.salvaSquadraDiGiocatori(squadreDiGiocatoriDTO);
    }



}
