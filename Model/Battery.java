package Model;

import Esper.Config;
//import Events.SelfTest;
//import java.io.IOException;
//import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Battery extends Thread implements Display {
    private final Controller controller;
    
    private double batteryLevel;
    private double warningLevel;
    private boolean powerState;

    public Battery(Controller c, double batteryLevel, double warningLevel, boolean powerState) {
        this.controller = c;
        this.batteryLevel = batteryLevel;
        this.warningLevel = warningLevel;
        this.powerState = powerState;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(double warningLevel) {
        this.warningLevel = warningLevel;
    }

    public boolean isBatteryLow() {
        return (batteryLevel < warningLevel);
    }

    public void setPowerState(boolean powerstate) {
        this.powerState = powerstate;
    }

    public boolean getPowerState() {
        return powerState;
    }

    public void decreaseBatterylevel() {
        batteryLevel = batteryLevel - 0.5;
    }

    @Override
    public void display(String s) {
        if (powerState == true) {
            System.out.println("The battery is on and the charge is " + getBatteryLevel());
        } else {
            System.out.println("The battery is off");
        }

        for (int i = 100; i >= 0; i--) {
            // for every min the battery working the charge will decrease by 1% of the battery precentage 
            setBatteryLevel(i);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Battery.class.getName()).log(Level.SEVERE, null, ex);
            }
//            Config.sendEvent(new SelfTest(batteryLevel, warningLevel, powerState));
        }
    }

}
