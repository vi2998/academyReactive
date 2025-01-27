package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;

import java.sql.SQLException;
import java.util.List;

public interface ITorneoDao {
    TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException;
    void eliminaTorneo(int id) throws SQLException;
    TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException;
    List<TorneoModel> cercaTorneiAndSquadre() throws SQLException;
}