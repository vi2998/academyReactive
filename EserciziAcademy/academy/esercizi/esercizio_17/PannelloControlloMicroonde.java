package academy.esercizi.esercizio_17;

public class PannelloControlloMicroonde {
    //TODO RIORDINARE CLASSE!!
    /*    TODO PULSANTE per cambiare il livello di potenza (che può avere i valori 1 o 2),
     */
    private int timer;
    private int powerLevel;
    private boolean acceso;

    public PannelloControlloMicroonde(boolean acceso, int timer, int powerLevel) {
        this.acceso = acceso;
        this.timer = timer;
        this.powerLevel = powerLevel;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    public void setPowerLevel(int powerLevel) {
        if (powerLevel > 0 && powerLevel < 3) {
            this.powerLevel = powerLevel;
        }
    }
        public int getPowerLevel () {
            return powerLevel;
        }

        public void plus30 () {
            timer += 30;
        }

        public void pulsanteReset () {
            setAcceso(false);
            setTimer(0);
            setPowerLevel(0);
        }

        public void pulsanteStart ( boolean start){
            if (start) {
                System.out.println("Cooking for " + getTimer() + " seconds at level " + getPowerLevel());
            } else {
                System.out.println("Microonde spento");
            }
        }

        public String toString () {
            return "timer = " + getTimer() +
                    ", powerLevel = " + getPowerLevel() +
                    ", start = " + acceso;
        }
    }

