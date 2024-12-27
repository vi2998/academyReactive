package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.model.GiocatoreModel;

public interface IGiocatoreDao {
    GiocatoreModel aggiornaAmmonizione(GiocatoreDTO giocatoreDTO) throws Exception;

}
