package it.reactive.torneoDemoMongo.service;


import it.reactive.torneoDemoMongo.dto.GiocatoreDTO;
import it.reactive.torneoDemoMongo.dto.SquadraDTO;
import it.reactive.torneoDemoMongo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemoMongo.dto.TifoseriaDTO;
import it.reactive.torneoDemoMongo.mapper.SquadraMapper;
import it.reactive.torneoDemoMongo.model.SquadraModel;
import it.reactive.torneoDemoMongo.repository.dao.iSquadraDao;
import it.reactive.torneoDemoMongo.resource.SquadraResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service

public class SquadraService {

    @Autowired
    iSquadraDao iSquadraDao;

    @Autowired
    SquadraMapper squadraMapper;

    public void rimuoviSquadra(ObjectId id) throws SQLException {
        iSquadraDao.rimuoviSquadra(id);
    }

    public SquadraResponse aggiungiGiocatore(ObjectId id, GiocatoreDTO giocatoreDTO) throws SQLException {
       return squadraMapper.fromModelToResource(iSquadraDao.aggiungiGiocatore(id, giocatoreDTO));
    }

    public SquadraResponse aggiungiTifoseria(ObjectId id, TifoseriaDTO tifoseriaDTO) throws SQLException {
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
