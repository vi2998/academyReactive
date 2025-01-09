package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;

import java.sql.SQLException;

public interface ITorneoDao {
    TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException;
    void eliminaTorneo(int id) throws SQLException;
    TorneoModel update(int idTorneo, int idSquadra);
}
