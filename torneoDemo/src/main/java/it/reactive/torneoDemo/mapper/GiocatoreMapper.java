package it.reactive.torneoDemo.mapper;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.resource.GiocatoreResponse;
import org.springframework.stereotype.Component;

@Component
public class GiocatoreMapper {
    public GiocatoreResponse fromModelToResponse(GiocatoreModel giocatoreModel) {
        GiocatoreResponse giocatoreResponse = new GiocatoreResponse();
        giocatoreResponse.setIdGiocatore(giocatoreModel.getIdGiocatore());
        giocatoreResponse.setNomeCognome(giocatoreModel.getNomeCognome());
        giocatoreResponse.setNumeroAmmonizioni(giocatoreModel.getNumeroAmmonizioni());
        return giocatoreResponse;
    }

    public GiocatoreModel fromDtoToModel(GiocatoreDTO giocatoreDTO){
        GiocatoreModel giocatoreModel = new GiocatoreModel();
        giocatoreModel.setNomeCognome(giocatoreDTO.getNomeCognome());
        return giocatoreModel;
    }
}
