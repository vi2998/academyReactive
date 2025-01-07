package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TorneoService {

    @Autowired
    ITorneoDao iTorneoDao;

    public void aggiungiTorneo(TorneoDTO torneoDTO){
        iTorneoDao.aggiungiTorneo(torneoDTO);
    }

    public void eliminaTorneo(int id){
        iTorneoDao.eliminaTorneo(id);
    }

    public void read(){
        iTorneoDao.read();
    }

    public void update(int idTorneo, Integer idSquadra){
        iTorneoDao.update(idTorneo, idSquadra);
    }
}
