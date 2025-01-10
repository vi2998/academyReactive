package it.reactive.torneoDemo.mapper;

import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.resource.TifoseriaResponse;
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
