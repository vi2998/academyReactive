package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.TorneoDTO;

public interface ITorneoDao {
    TorneoModel create(TorneoDTO torneoDTO);
    TorneoModel read(int id);
    TorneoModel update(int id, TorneoDTO torneoDTO);
    TorneoModel delete(int id);
}
