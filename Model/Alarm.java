package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Yousef Hussien
 */
public class Alarm {
    private boolean sound = true;
    
    public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        // If sound muted, return
        if (!sound)
            return;
        
        String audioFile = "./assets/beep.wav";
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(audioFile));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength()/1000);
        Thread.currentThread().interrupt();
        clip.close();
    }
    
    public void muteSound(boolean mute) {
        this.sound = !mute;
    }
}
