package it.reactive.torneoDemoMongo.repository.dao;

import it.reactive.torneoDemoMongo.dto.TorneoDTO;
import it.reactive.torneoDemoMongo.model.TorneoModel;

import java.sql.SQLException;
import java.util.List;

public interface iTorneoDao {

    TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException;
    void eliminaTorneo(int id) throws SQLException;
    TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException;
    List<TorneoModel> cercaTorneiAndSquadre() throws SQLException;

}
