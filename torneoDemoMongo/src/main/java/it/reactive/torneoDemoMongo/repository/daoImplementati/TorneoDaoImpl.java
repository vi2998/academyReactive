package it.reactive.torneoDemoMongo.repository.daoImplementati;

import it.reactive.torneoDemoMongo.dto.TorneoDTO;
import it.reactive.torneoDemoMongo.model.TorneoModel;
import it.reactive.torneoDemoMongo.repository.dao.iTorneoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class TorneoDaoImpl implements iTorneoDao {
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
