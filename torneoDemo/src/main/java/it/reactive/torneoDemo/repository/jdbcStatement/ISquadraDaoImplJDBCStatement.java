package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class ISquadraDaoImplJDBCStatement implements ISquadraDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public SquadraModel aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO) {
        return null;
    }

    @Override
    public SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) {
        return null;
    }

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) {
        return null;
    }

    @Override
    public SquadraModel salvaSquadraDiGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO) {
        return null;
    }

    @Override
    public SquadraModel rimuoviSquadra(int id) {
        return null;
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) {
        return Collections.emptyList();
    }


}
