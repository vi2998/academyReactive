package it.reactive.torneoDemoMongo.service;

import it.reactive.torneoDemoMongo.dto.TorneoDTO;
import it.reactive.torneoDemoMongo.mapper.TorneoMapper;
import it.reactive.torneoDemoMongo.model.TorneoModel;
import it.reactive.torneoDemoMongo.repository.dao.iTorneoDao;
import it.reactive.torneoDemoMongo.resource.TorneoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TorneoService {

    @Autowired
     iTorneoDao iTorneoDao;

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

    public List<TorneoResponse> cercaTorneiAndSquadre() throws SQLException {
        List<TorneoModel> torneoModelList = iTorneoDao.cercaTorneiAndSquadre();
        List<TorneoResponse> torneoResponseList = new ArrayList<>();
        for (TorneoModel torneoModel : torneoModelList) {
            torneoResponseList.add(torneoMapper.fromModelToResponse(torneoModel));
            //FIXME
        }
        return torneoResponseList;
    }
}
