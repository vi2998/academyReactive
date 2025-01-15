package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_PREPAREDSTATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_PREPAREDSTATEMENT)

public class SquadraDaoImplJDBCPreparedStatement implements ISquadraDao {
    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        return null;
    }

    @Override
    public SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) throws SQLException {
        return null;
    }

    @Override
    public SquadraModel aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO) throws SQLException {
        return null;
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public void rimuoviSquadra(int id) throws SQLException {

    }
}
