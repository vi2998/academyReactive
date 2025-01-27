package it.reactive.torneoDemoMongo.mapper;

import it.reactive.torneoDemoMongo.model.GiocatoreModel;
import it.reactive.torneoDemoMongo.resource.GiocatoreResponse;
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
}
