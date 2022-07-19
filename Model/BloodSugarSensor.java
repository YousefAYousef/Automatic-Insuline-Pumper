package Model;

//import esper.config;
//import events.BloodSugarReading;
//import events.StateEvent;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BloodSugarSensor extends Thread implements HardwareDevice {
    private final Controller controller;
    
    private double sugarLvl;
    private boolean hasIssue;

    public BloodSugarSensor(Controller c) {
        this.controller = c;
        this.sugarLvl = 10;
        this.hasIssue = false;
    }

    public boolean checkHardware() {
        new Thread(() -> {
            hasIssue = true;
        }).start();
        return hasIssue;
    }

    public double CaptureSugarLevel() {
        return 110;
    }

}
