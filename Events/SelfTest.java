/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Events;

import Esper.Config;
import Model.Battery;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SelfTest{

    private final double batteryLevel;
    private final double warningLevel;
    private final boolean PowerState;
   
    public SelfTest(double batteryLevel, double warningLevel, boolean PowerState) {
        this.batteryLevel = batteryLevel;
        this.warningLevel = warningLevel;
        this.PowerState = PowerState;
    }

    public double getbatterLevel(){
        return batteryLevel;
    }
    public double getwarningLevel(){
        return warningLevel;
    }
    public boolean getPowerState(){
        return PowerState;
    }

  
}
