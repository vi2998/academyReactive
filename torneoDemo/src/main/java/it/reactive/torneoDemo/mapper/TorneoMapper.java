package it.reactive.torneoDemo.mapper;

import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.resource.SquadraResponse;
import it.reactive.torneoDemo.resource.TorneoResponse;
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
        for (SquadraModel squadraModel : torneoModel.getSquadre()) {
            listaSquadre.add(squadraMapper.fromModelToResource(squadraModel));
        }
        torneoResponse.setSquadre(listaSquadre);
        return torneoResponse;
    }
}
