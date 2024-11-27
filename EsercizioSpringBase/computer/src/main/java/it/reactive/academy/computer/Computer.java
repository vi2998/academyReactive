package it.reactive.academy.computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Computer {

    // Dependency Injection a livello di attributo
//    @Autowired
//    private HardDisk hardDisk;
//    @Autowired
//    private Schermo schermo;


    //    public void saluta(){
    //     System.out.println(hardDisk.getTipo());
    //    }

    // Dependency Injection a livello di costruttore
    private HardDisk hardDisk;
    private Schermo schermo;
    
    @Autowired
    public Computer(HardDisk hardDisk, Schermo schermo) {
        this.hardDisk = hardDisk;
        this.schermo = schermo;
    }

    @Autowired(required=false)
    private Tastiera tastiera;

    @Autowired
    @Qualifier("windows")
    private SistemaOperativo sistemaOperativo;


    public void saluta() {
        System.out.println("Sono il computer");
        System.out.println("uso lo schermo: " + schermo.getSchermo());
        System.out.println("sono configurato con il sistema operativo: " + sistemaOperativo.getNome());
        System.out.println("ed il linguaggio: " + sistemaOperativo.getLinguaggio());
        System.out.println("Il separatore di linee: " + sistemaOperativo.getLineSeparator());
        if (tastiera != null) {
            System.out.println("uso la tastiera: " + tastiera.tasti());
        } else {
            System.out.println("non hai configurato la tastiera");
        }
        System.out.println("l'hard disk scelto è:" + hardDisk.getTipo());
    }

}