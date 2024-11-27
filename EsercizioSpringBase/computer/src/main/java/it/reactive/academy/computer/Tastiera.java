package it.reactive.academy.computer;

import org.springframework.stereotype.Component;

@Component
public class Tastiera {
    private String tipoTastiera;

    public Tastiera(String tipoTastiera) {
        this.tipoTastiera = tipoTastiera;
    }

    public String tasti(){
        return tipoTastiera;
    }

    public void setTastiera(String layout) {
        tipoTastiera = layout;
    }
}
