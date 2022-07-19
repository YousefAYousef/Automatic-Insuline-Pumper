package Model;

//import esper.config;
//import events.StateEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsulinPumper extends Thread implements HardwareDevice {
    private final Controller controller;
    
    private boolean hasIssue;

    public InsulinPumper(Controller c) {
        this.controller = c;
        hasIssue = false;
    }

    @Override
    public boolean checkHardware() {
        new Thread(() -> {
            hasIssue = false;
        }).start();
        return hasIssue;
    }

    public void pumpInsulin(double insulinDose) {
        
    }

}
