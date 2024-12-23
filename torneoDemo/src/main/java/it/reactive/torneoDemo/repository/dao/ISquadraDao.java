package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;

public interface ISquadraDao {
    SquadraModel create(SquadraDTO squadraDTO);
    SquadraModel read(int id);
    SquadraModel update(int id, SquadraDTO squadraDTO);
    SquadraModel delete(int id);
}
//SQUADRE DI GIOCQTORI DAO???