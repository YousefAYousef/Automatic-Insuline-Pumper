package Model;

//import Esper.Config;
//import Events.StateEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsulinReservoirSensor extends Thread implements HardwareDevice {
    private final Controller controller;

    private double capacity;
    private double currentCapacity;
    private boolean hasIssue;

    public InsulinReservoirSensor(Controller c, double capacity) {
        this.controller = c;
        this.capacity = capacity;
        this.currentCapacity = capacity;
        this.hasIssue = false;
    }

    public double CaptureReservoirCapacity() {
        currentCapacity = 60;
        return currentCapacity;
    }
    
    public boolean CollectInsulinDose(double dose) {
        if (dose > currentCapacity)
            return false;
        
        currentCapacity -= dose;
        return true;
    }
    
    @Override
    public boolean checkHardware() {
        new Thread(() -> {
            hasIssue = true;
        }).start();
        return hasIssue;
    }
}
