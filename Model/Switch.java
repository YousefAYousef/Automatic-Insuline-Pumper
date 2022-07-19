/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.logging.Level;
import org.apache.log4j.Logger;

public class Switch extends Thread {

    private final Controller controller;
    private boolean power;

    public Switch(Controller c) {
        this.controller = c;
        power = true;
    }

    public Controller getCont() {
        return controller;
    }

    public void powerOn() {
        power = true;
    }

    public void powerOff() {
        power = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (controller.getState() == SystemState.NORMAL) {
                    this.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Switch.class.getName()).log(null, ex);

            }
        }
    }
}
