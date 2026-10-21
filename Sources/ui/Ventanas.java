
//Clase realizada para personalizar ventanas emergentes coincidentes en todos los juegos 

package ui;

import domain.APP;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import util.Imagen;

public class Ventanas {

    //VENTANA EMERGENTE CUANDO SE GANA
    public static void mostrarVentanaGanador(JFrame frame) {
        JDialog dialogo = new JDialog(frame, "¡Has ganado!", true);
        dialogo.setSize(500,500);
        dialogo.setLocationRelativeTo(frame);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon imagen=Imagen.escalarImagen("ui\\images\\juego_superado.png", 400, 400);
        JLabel mensaje = new JLabel(imagen, JLabel.CENTER);

        JButton continuar = new JButton("Volver a APP");
        continuar.addActionListener(e -> {
            dialogo.dispose();    // Cierra el JDialog
            frame.dispose();    // Cierra la ventana principal
            new APP(); // Abre la nueva APP
        });
        continuar.setBackground(Color.pink);
        continuar.setFont(new Font("SansSerif", Font.BOLD, 20));
        continuar.setForeground(Color.white);

        panel.add(mensaje, BorderLayout.CENTER);
        panel.add(continuar, BorderLayout.SOUTH);

        dialogo.setContentPane(panel);
        //Cuando se cierra la ventana no simplemente se cierra si no que se debe volver al menu principal
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialogo.dispose();
                frame.dispose();
                new APP();
            }
        });
        dialogo.setVisible(true);
    }

    //VENTANA EMERGENTE CUANDO SE PIERDE
    public static void mostrarVentanaPerdedor(JFrame frame) {
        JDialog dialogo = new JDialog(frame, "¡Has perdido!", true);
        dialogo.setSize(500, 500);
        dialogo.setLocationRelativeTo(frame);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon imagen=Imagen.escalarImagen("ui\\images\\juego_terminado.png", 400, 400);
        JLabel mensaje = new JLabel(imagen, JLabel.CENTER);

        JButton continuar = new JButton("Volver a APP");
        continuar.addActionListener(e -> {
            dialogo.dispose();
            frame.dispose();
            new APP();
        });
        continuar.setBackground(Color.pink);
        continuar.setFont(new Font("SansSerif", Font.BOLD, 20));
        continuar.setForeground(Color.white);

        panel.add(mensaje, BorderLayout.CENTER);
        panel.add(continuar, BorderLayout.SOUTH);

        dialogo.setContentPane(panel);
        //Cuando se da a la X no se puede cerrar solo si no que en este caso se ha perdido y se debe hacer las mismas acciones que si se da al boton continuar, es una forma de evitar errores
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialogo.dispose();
                frame.dispose();
                new APP();
            }
        });
        dialogo.setVisible(true);
    }

    //VENTANA EMERGENTE SIGUIENTE NIVEL 
    public static void mostrarventanaSiguienteNivel(JFrame frame)
    {
        JDialog dialogo = new JDialog(frame, "¡CONTINUA ASI!", true);
        dialogo.setSize(500, 500);
        dialogo.setLocationRelativeTo(frame);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon imagen=Imagen.escalarImagen("ui\\images\\nivel_superado.png", 400, 400);
        JLabel mensaje = new JLabel(imagen, JLabel.CENTER);

        JButton continuar = new JButton("Continuar");
        //Equivalente a sobreescribir el metodo
        continuar.addActionListener(e -> {
            dialogo.dispose();
        });
        continuar.setBackground(Color.pink);
        continuar.setFont(new Font("SansSerif", Font.BOLD, 20));
        continuar.setForeground(Color.white);

        panel.add(mensaje, BorderLayout.CENTER);
        panel.add(continuar, BorderLayout.SOUTH);

        dialogo.setContentPane(panel);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setVisible(true);
    }

    //VENTANA EMERGENTE SE HA FALLADO
    public static void mostarventanaFallaste(JFrame frame)
    {
        JDialog dialogo = new JDialog(frame, "¡HAS FALLADO!", true);
        dialogo.setSize(500, 500);
        dialogo.setLocationRelativeTo(frame);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon imagen=Imagen.escalarImagen("ui\\images\\vida_menos.png", 400, 400);
        JLabel mensaje = new JLabel(imagen, JLabel.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton continuar = new JButton("Continuar");
        continuar.addActionListener(e -> {
            dialogo.dispose();
        });
        continuar.setBackground(Color.pink);
        continuar.setFont(new Font("SansSerif", Font.BOLD, 20));
        continuar.setForeground(Color.white);

        panel.add(mensaje, BorderLayout.CENTER);
        panel.add(continuar, BorderLayout.SOUTH);

        dialogo.setContentPane(panel);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setVisible(true);
    }
}
