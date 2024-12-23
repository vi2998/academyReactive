package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;

public interface IGiocatoreDao {
    GiocatoreModel create(GiocatoreDTO giocatoreDTO);
    GiocatoreModel read(int id);
    GiocatoreModel update(int id, GiocatoreDTO GiocatoreDTO);
    GiocatoreModel delete(int id);
}
