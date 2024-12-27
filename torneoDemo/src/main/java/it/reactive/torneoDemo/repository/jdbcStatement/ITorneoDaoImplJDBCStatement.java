package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;

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
    public TorneoModel update(int id, TorneoDTO torneoDTO) {
        return null;
    }

    @Override
    public TorneoModel read(int id) {
        return null;
    }
}
