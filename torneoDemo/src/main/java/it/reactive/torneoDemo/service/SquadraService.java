package it.reactive.torneoDemo.service;


import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.mapper.SquadraMapper;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import it.reactive.torneoDemo.resource.SquadraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SquadraService {

    @Autowired
    ISquadraDao iSquadraDao;

    @Autowired
    SquadraMapper squadraMapper;

    public void rimuoviSquadra(int id) throws SQLException {
        iSquadraDao.rimuoviSquadra(id);
    }

    public SquadraResponse aggiungiGiocatore(int id, GiocatoreDTO giocatoreDTO) throws SQLException {
       return squadraMapper.fromModelToResource(iSquadraDao.aggiungiGiocatore(id, giocatoreDTO));
    }

    public SquadraResponse aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) throws SQLException {
        return squadraMapper.fromModelToResource(iSquadraDao.aggiungiTifoseria(id, tifoseriaDTO));
    }

    public SquadraResponse salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        return squadraMapper.fromModelToResource(iSquadraDao.salvaSquadra(squadraDTO));
    }

    public SquadraResponse salvaSquadraConGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO) throws SQLException {
        SquadraModel squadraModel = iSquadraDao.salvaSquadra(squadreDiGiocatoriDTO);
        for (GiocatoreDTO giocatoreDTO : squadreDiGiocatoriDTO.getListaGiocatori()) {
           squadraModel = iSquadraDao.aggiungiGiocatore(squadraModel.getIdSquadra(), giocatoreDTO);
        }
        return squadraMapper.fromModelToResource(squadraModel);
    }

    public List<SquadraResponse> ricercaSquadre(boolean ricercaGiocatori) throws SQLException{
        List<SquadraModel> squadraModelList = iSquadraDao.ricercaSquadre(ricercaGiocatori);
        List<SquadraResponse> squadraResponseList = new ArrayList<>();
        for (SquadraModel squadraModel : squadraModelList) {
            if (ricercaGiocatori){
                squadraResponseList.add(squadraMapper.fromModelToResource(squadraModel));
            }else{
                squadraResponseList.add(squadraMapper.fromModelToResourceSenzaGiocatori(squadraModel));
            }
        }
        return squadraResponseList;
    }

}
