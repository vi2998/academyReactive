package it.reactive.torneoDemo.repository.dao;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.model.GiocatoreModel;

import java.sql.SQLException;

public interface IGiocatoreDao {

    GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException;
}
