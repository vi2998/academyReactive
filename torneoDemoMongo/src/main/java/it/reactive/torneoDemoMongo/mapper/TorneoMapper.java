package it.reactive.torneoDemoMongo.mapper;

import it.reactive.torneoDemoMongo.model.SquadraModel;
import it.reactive.torneoDemoMongo.model.TorneoModel;
import it.reactive.torneoDemoMongo.resource.SquadraResponse;
import it.reactive.torneoDemoMongo.resource.TorneoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TorneoMapper {

    @Autowired
    SquadraMapper squadraMapper;

    public TorneoResponse fromModelToResponse(TorneoModel torneoModel) {
        TorneoResponse torneoResponse = new TorneoResponse();
        torneoResponse.setIdTorneo(torneoModel.getIdTorneo());
        torneoResponse.setNomeTorneo(torneoModel.getNomeTorneo());
        Set<SquadraResponse> listaSquadre = new HashSet<>();
        if (torneoModel.getSquadre() != null){
            for (SquadraModel squadraModel : torneoModel.getSquadre()) {
                listaSquadre.add(squadraMapper.fromModelToResource(squadraModel));
            }
        }
        torneoResponse.setSquadre(listaSquadre);
        return torneoResponse;
    }
}
