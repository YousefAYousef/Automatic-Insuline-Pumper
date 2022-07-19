package Model;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Yousef Hussien
 */
public class Display_1 implements Display, Runnable {
    
    private static Display_1 INSTANCE;
    private String msg;
    private final Alarm alarm = new Alarm();
    private final Queue<String> msgQueue = new LinkedList<>();

    public static Display_1 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Display_1();
        }
        return INSTANCE;
    }

    @Override
    public void display(String code) {
        for (Message m :
                Message.values()) {
            if (msg == null)
                msg = code.toUpperCase().equals(m.name()) ? m.toString() : null;
        }
        String ERROR_MESSAGE = "Fix your shit";
        System.out.println(msg != null ? msg : ERROR_MESSAGE);
        msg = null;
    }

    public void addToBuffer(String s) {
        msgQueue.add(s);
    }

    @Override
    public void run() {
        do {
            System.out.println(msgQueue.size());
            String message = msgQueue.poll();
            display(message);
            try {
                alarm.play();
                // Display Duration
                Thread.sleep(1000);
            } catch (UnsupportedAudioFileException | InterruptedException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            if (msgQueue.size() <= 0) {
                System.out.println();
            }
        } while (msgQueue.size() > 0);
    }
}
