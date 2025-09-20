import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CardFlipPlayer {
    static Clip clip;
    static AudioInputStream audioInputStream;
    static String filePath;

    public CardFlipPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        filePath = "Audio/card.wav";

        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        clip = AudioSystem.getClip();

        clip.open(audioInputStream);
    }

    public void play() {
        clip.start();
    }
}