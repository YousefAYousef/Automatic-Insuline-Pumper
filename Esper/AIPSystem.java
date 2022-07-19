package Esper;

import Model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Yousef 
 */
public class AIPSystem {
    
        public static void main(String[] args) {

        // Disable logging
        Logger.getRootLogger().setLevel(Level.OFF);

        // Register events
        Config.registerEvents();

        // Create Kettle
//        final Kettle kettle = new Kettle();

        Config.createStatement("select temp from TempSensorReading")
                .setSubscriber(new Object() {
                    public void update(int temp) throws InterruptedException {
//                        kettle.tempSignal(temp);
                    }
                });

        Config.createStatement("select state from PowerEvent")
                .setSubscriber(new Object() {
                    public void update(boolean state) {
//                        kettle.setState(state);
                    }
                });
        
        
        
        
        // Display 1 & Alarm Test //
//        Display_1 display_1 = Display_1.getInstance();
//        display_1.addToBuffer("low_battery");
//        Thread t = new Thread(display_1);
//        t.start();
//        display_1.addToBuffer("low_battery");
//        display_1.addToBuffer("pump");
//        display_1.addToBuffer("emergency");
    }  
}
