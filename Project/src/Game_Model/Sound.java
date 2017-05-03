package Game_Model;

import gameView.Menu.Settings_Menu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Apple on 29/04/2017.
 */
public class Sound implements LineListener{

    boolean playCompleted = true;


    public void playSound(String audioFilePath) {

        File audioFile = new File(audioFilePath);

        if(playCompleted && Settings_Menu.isSelected()) {

            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                AudioFormat format = audioStream.getFormat();

                DataLine.Info info = new DataLine.Info(Clip.class, format);

                Clip audioClip = (Clip) AudioSystem.getLine(info);

                audioClip.addLineListener(this);

                audioClip.open(audioStream);

                audioClip.start();

            } catch (UnsupportedAudioFileException ex) {
                System.out.println("The specified audio file is not supported.");
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Error playing the audio file.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
            playCompleted = false;

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }
}
