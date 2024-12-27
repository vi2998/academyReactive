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

    public void read(int id){
        iTorneoDao.read(id);
    }

    public void update(int id, TorneoDTO torneoDTO){
        iTorneoDao.update(id, torneoDTO);
    }
}
