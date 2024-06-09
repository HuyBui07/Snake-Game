package utils;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class SoundManager {

    private static HashMap<String, Clip> soundClips = new HashMap<String, Clip>();

    public static void loadSound(String soundFile) {
        File file = new File(soundFile);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            soundClips.put(soundFile, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playCrunchSound() {
        Clip clip = soundClips.get("src/sounds/crunch.wav");
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playLowVolumeSound(String string) {
        Clip clip = soundClips.get(string);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-50.0f);
        clip.start();
    }
}
