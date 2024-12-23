package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;

public interface ITifoseriaDao {
    TifoseriaModel create(TifoseriaDTO tifoseriaDTO);
    TifoseriaModel read(int id);
    TifoseriaModel update(int id, TifoseriaDTO tifoseriaDTO);
    TifoseriaModel delete(int id);

}
