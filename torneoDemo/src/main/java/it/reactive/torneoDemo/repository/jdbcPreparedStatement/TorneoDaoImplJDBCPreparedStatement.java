package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_PREPAREDSTATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_PREPAREDSTATEMENT)

public class TorneoDaoImplJDBCPreparedStatement implements ITorneoDao {
    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        return null;
    }

    @Override
    public void eliminaTorneo(int id) throws SQLException {

    }

    @Override
    public TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException {
        return null;
    }

    @Override
    public List<TorneoModel> cercaTorneiAndSquadre() throws SQLException {
        return Collections.emptyList();
    }
}
