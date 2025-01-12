package it.reactive.torneoDemo.mapper;

import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.resource.GiocatoreResponse;
import it.reactive.torneoDemo.resource.SquadraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SquadraMapper {

    @Autowired
    TifoseriaMapper tifoseriaMapper;

    @Autowired
    GiocatoreMapper giocatoreMapper;

    public SquadraResponse fromModelToResource(SquadraModel squadraModel) {
        SquadraResponse squadraResponse = new SquadraResponse();
        squadraResponse.setIdSquadra(squadraModel.getIdSquadra());
        squadraResponse.setNome(squadraModel.getNome());
        squadraResponse.setColoriSociali(squadraModel.getColoriSociali());
        if (squadraModel.getTifoseria() != null) {
            squadraResponse.setTifoseria(tifoseriaMapper.fromModelToResource(squadraModel.getTifoseria()));
        }


        Set<GiocatoreResponse> listaGiocatori = new HashSet<>();
        if (squadraModel.getGiocatori() != null) {
            for (GiocatoreModel giocatoreModel : squadraModel.getGiocatori()) {
                GiocatoreResponse giocatoreResource = giocatoreMapper.fromModelToResource(giocatoreModel);
                listaGiocatori.add(giocatoreResource);
            }

        }
        squadraResponse.setGiocatori(listaGiocatori);

        return squadraResponse;
    }

    public SquadraResponse fromModelToResourceSenzaGiocatori(SquadraModel squadraModel) {
        SquadraResponse squadraResource = new SquadraResponse();
        squadraResource.setIdSquadra(squadraModel.getIdSquadra());
        squadraResource.setNome(squadraModel.getNome());
        squadraResource.setColoriSociali(squadraModel.getColoriSociali());
        if (squadraModel.getTifoseria() != null) {
            squadraResource.setTifoseria(tifoseriaMapper.fromModelToResource(squadraModel.getTifoseria()));
        }
        return squadraResource;
    }
}
