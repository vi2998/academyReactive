package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TorneoService {

    @Autowired
    ITorneoDao iTorneoDao;

    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        return iTorneoDao.aggiungiTorneo(torneoDTO);
    }

    public void eliminaTorneo(int id) throws SQLException {
        iTorneoDao.eliminaTorneo(id);
    }

    public void update(int idTorneo, Integer idSquadra){
        iTorneoDao.update(idTorneo, idSquadra);
    }
}
