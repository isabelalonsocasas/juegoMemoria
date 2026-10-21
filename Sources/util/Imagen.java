
//Clase creada para escalar imagenes ya que durante todo el cógido era un método muy utilizado, lo hacemos static para no crearnos una instancia cada vez

package util;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Imagen {

    public static ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        ImageIcon original = new ImageIcon(ruta);
        if (original.getImage() == null) return null;
        Image img = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    
}
