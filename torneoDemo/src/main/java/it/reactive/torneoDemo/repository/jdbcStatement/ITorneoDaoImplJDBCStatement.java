package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class ITorneoDaoImplJDBCStatement implements ITorneoDao {
    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) {
        return null;
    }

    @Override
    public TorneoModel eliminaTorneo(int id) {
        return null;
    }

    @Override
    public TorneoModel update(int idTorneo, int idSquadra) {
        return null;
    }

    @Override
    public TorneoModel read() {
        return null;
    }
}
