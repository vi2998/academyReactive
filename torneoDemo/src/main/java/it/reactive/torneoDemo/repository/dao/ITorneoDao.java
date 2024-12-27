package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;

public interface ITorneoDao {
    TorneoModel aggiungiTorneo(TorneoDTO torneoDTO);
    TorneoModel eliminaTorneo(int id);
    TorneoModel update(int id, TorneoDTO torneoDTO);
    TorneoModel read(int id);
}
