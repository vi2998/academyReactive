package it.reactive.torneoDemo.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SquadreDiGiocatoriDTO extends SquadraDTO{

    @NotNull
    @Valid
    private List<GiocatoreDTO> listaGiocatori;

    public List<GiocatoreDTO> getListaGiocatori() {
        return listaGiocatori;
    }

    public void setListaGiocatori(List<GiocatoreDTO> listaGiocatori) {
        this.listaGiocatori = listaGiocatori;
    }
}
