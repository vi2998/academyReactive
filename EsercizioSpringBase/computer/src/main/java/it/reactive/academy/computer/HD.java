package it.reactive.academy.computer;

import org.springframework.stereotype.Component;


public class HD implements HardDisk{

    @Override
    public String getTipo() {
        return "HD";
    }
}
