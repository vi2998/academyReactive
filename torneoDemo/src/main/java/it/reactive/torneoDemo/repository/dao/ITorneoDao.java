package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;

public interface ITorneoDao {
    TorneoModel aggiungiTorneo(TorneoDTO torneoDTO);
    TorneoModel eliminaTorneo(int id);
    TorneoModel update(int idTorneo, int idSquadra);
    TorneoModel read();
}
