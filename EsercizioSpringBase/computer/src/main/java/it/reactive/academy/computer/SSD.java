package it.reactive.academy.computer;

import org.springframework.stereotype.Component;


public class SSD implements HardDisk{

    @Override
    public String getTipo() {
        return "SSD";
    }
}
