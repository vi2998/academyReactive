package it.reactive.torneoDemoMongo.mapper;

import it.reactive.torneoDemoMongo.model.TifoseriaModel;
import it.reactive.torneoDemoMongo.resource.TifoseriaResponse;
import org.springframework.stereotype.Component;

@Component
public class TifoseriaMapper {
    public TifoseriaResponse fromModelToResource(TifoseriaModel tifoseriaModel) {
        TifoseriaResponse tifoseriaResponse = new TifoseriaResponse();
        tifoseriaResponse.setIdTifoseria(tifoseriaModel.getIdTifoseria());
        tifoseriaResponse.setNomeTifoseria(tifoseriaModel.getNomeTifoseria());
        return tifoseriaResponse;
    }
}