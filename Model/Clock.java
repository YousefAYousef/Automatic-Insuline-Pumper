package Model;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private Controller controller;
    
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            System.out.println();
            //every 30 sec the timer will sent notify to controller to make a self test
            timer.schedule(task, 0, 30000);
            //every 10 min the timer will sent to the controller to make a periodic test in case we in normal state
            timer.schedule(task, 0, 600000);
            //in case we in emergency state sent to controller to make a periodic test every 5 min
            timer.schedule(this, 0, 300000);

            while (true) {

            }
        }
    };

}
