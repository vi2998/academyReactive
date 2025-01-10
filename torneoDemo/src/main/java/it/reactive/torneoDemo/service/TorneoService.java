package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.mapper.TorneoMapper;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import it.reactive.torneoDemo.resource.TorneoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TorneoService {

    @Autowired
    ITorneoDao iTorneoDao;

    @Autowired
    TorneoMapper torneoMapper;

    public TorneoResponse aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        return torneoMapper.fromModelToResponse(iTorneoDao.aggiungiTorneo(torneoDTO));
    }

    public void eliminaTorneo(int id) throws SQLException {
        iTorneoDao.eliminaTorneo(id);
    }

    public TorneoResponse associaTorneoASquadra(int idTorneo, Integer idSquadra) throws SQLException {
       return torneoMapper.fromModelToResponse(iTorneoDao.associaTorneoASquadra(idTorneo, idSquadra));
    }
}
