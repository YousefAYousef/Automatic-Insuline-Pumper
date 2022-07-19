/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esper;

import Events.*;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 *
 * @author Yousef 
 */
public class Config {
    
    private static EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();

    public static void registerEvents() {
        // Shakal
        engine.getEPAdministrator().getConfiguration().addEventType(SelfTest.class);
        // Essam
        engine.getEPAdministrator().getConfiguration().addEventType(BloodSugarReading.class);
        engine.getEPAdministrator().getConfiguration().addEventType(InsulinReservoirReading.class);
        engine.getEPAdministrator().getConfiguration().addEventType(StateEvent.class);
        
        System.out.println("Events Successfully Registered.");
    }

    public static EPStatement createStatement(String s) {
        EPStatement result = engine.getEPAdministrator().createEPL(s);
        System.out.println("EPL Statement Successfully Created.");
        return result;
    }
    
    public static void sendEvent(Object o)
    {
        engine.getEPRuntime().sendEvent(o);
    }
}
