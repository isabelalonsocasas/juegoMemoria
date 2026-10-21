package util;
import java.io.File;
import javax.sound.sampled.*;

//Se ha buscado informacion de como importar un audio al programa y se ha creado esta clase para manejar mejor los audios.

public class Sonido {

    public static void reproducir(String ruta) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Clip reproducirEnBucle(String ruta) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Música en bucle
            clip.start();
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}