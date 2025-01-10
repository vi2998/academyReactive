package it.reactive.torneoDemo.mapper;

import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.resource.GiocatoreResponse;
import org.springframework.stereotype.Component;

@Component
public class GiocatoreMapper {
    public GiocatoreResponse fromModelToResource(GiocatoreModel giocatoreModel) {
        GiocatoreResponse giocatoreResource = new GiocatoreResponse();
        giocatoreResource.setIdGiocatore(giocatoreModel.getIdGiocatore());
        giocatoreResource.setNomeCognome(giocatoreModel.getNomeCognome());
        giocatoreResource.setNumeroAmmonizioni(giocatoreModel.getNumeroAmmonizioni());
        return giocatoreResource;
    }
}
