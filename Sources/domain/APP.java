package domain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import ui.Juego_Cartas;
import ui.Juego_Coordinacion;
import ui.Juego_memoria;
import util.Imagen;

//Clase principal. Aunque es visual, se ubica en domain porque gestiona toda la lógica y navegación entre juegos es el MENU PRINCIPAL

public class APP extends JFrame

{
    public static void main (String[] args){

        APP app = new APP();  
    }

    public APP() {
        super("SIEMPRE CONTIGO"); 
        initComponents(); 
        this.setSize(900,900);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

    }

    //VARIABLES
    Font t = new Font("Garamond",Font.BOLD, 40); 

    //COMPONENTES
    private void initComponents() {
        
        //Paneles
        JPanel panel_norte= new JPanel(); 
        panel_norte.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel_norte.setBackground(Color.pink);

        JPanel panel_central= new JPanel();
            panel_central.setLayout(new GridLayout(1, 3, 10,10));
            panel_central.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        
        //Componentes Norte
        JLabel titulo= new JLabel("SIEMPRE CONTIGO");
            titulo.setFont(t);
        JLabel etiqueta_logo= new JLabel(Imagen.escalarImagen("ui\\images\\logotipo.png",150, 80)); 

        //COMPONENTES PANEL CENTRAL (TRES BOTONES PARA JUGAR A CADA JUEGO)
        final APP pestaña=this; //Necesario que sea final para utilizarlo en las clases anónimas.

        JButton boton_juegocoordinacion= new JButton(); 
            //Se selecciona por defecto asi que le quitamos la selección por estética para que no salga encuadrado.
            boton_juegocoordinacion.setFocusPainted(false);
            boton_juegocoordinacion.setBackground(Color.white);
            boton_juegocoordinacion.setIcon(new ImageIcon("ui\\images\\colada.png"));
            boton_juegocoordinacion.setToolTipText("Si quieres jugar a coordinacion pulsa el botón");
            boton_juegocoordinacion.setBorder(new LineBorder(Color.pink, 10));
            boton_juegocoordinacion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    pestaña.dispose(); 
                    Juego_Coordinacion coordinacion=new Juego_Coordinacion();
                }
            });
            boton_juegocoordinacion.addMouseListener(new MouseAdapter() {
                //Al pasar el raton
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton_juegocoordinacion.setBackground(Color.PINK); 
                    boton_juegocoordinacion.setForeground(Color.black);
                    boton_juegocoordinacion.setIcon(null); 
                    boton_juegocoordinacion.setFont(new Font("Garamond", Font.BOLD, 40));
                    boton_juegocoordinacion.setText("CUELA LA COLADA");
                }
                //Cuando sale el raton
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton_juegocoordinacion.setIcon(new ImageIcon("ui\\images\\colada.png")); 
                    boton_juegocoordinacion.setBackground(Color.white);
                    boton_juegocoordinacion.setText(null);
                }
            });

        JButton boton_juegocartas= new JButton(); 
            boton_juegocartas.setBackground(Color.white);
            boton_juegocartas.setIcon(Imagen.escalarImagen("ui\\images\\abrazadas.jpg", 550, 1000));
            boton_juegocartas.setToolTipText("Si quieres jugar a hacer parejas pulsa el botón"); 
            boton_juegocartas.setBorder(new LineBorder(Color.pink, 10));
            boton_juegocartas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    pestaña.dispose(); 
                    Juego_Cartas juego=new Juego_Cartas();
                }
            });
            boton_juegocartas.addMouseListener(new MouseAdapter() {
                //Al pasar el raton
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton_juegocartas.setBackground(Color.PINK); 
                    boton_juegocartas.setForeground(Color.black);
                    boton_juegocartas.setIcon(null); 
                    boton_juegocartas.setFont(new Font("Garamond", Font.BOLD, 40));
                    boton_juegocartas.setText("EMPAREJADOS");
                }
                //Cuando sale el raton
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton_juegocartas.setIcon(Imagen.escalarImagen("ui\\images\\abrazadas.jpg", 550, 1000)); 
                    boton_juegocartas.setBackground(Color.white);
                    boton_juegocartas.setText(null);
                }
            });

        JButton boton_juegomemoria= new JButton(); 
            boton_juegomemoria.setBackground(Color.white);
            boton_juegomemoria.setIcon(new ImageIcon("ui\\images\\bailando.png"));
            boton_juegomemoria.setToolTipText("Si quieres jugar a la memoria pulsa el botón");
            boton_juegomemoria.setBorder(new LineBorder(Color.pink, 10));
            boton_juegomemoria.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    pestaña.dispose(); 
                    Juego_memoria memoria=new Juego_memoria();
                }
            });
            boton_juegomemoria.addMouseListener(new MouseAdapter() {
                //Al pasar el raton
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton_juegomemoria.setBackground(Color.PINK); 
                    boton_juegomemoria.setForeground(Color.black);
                    boton_juegomemoria.setIcon(null); 
                    boton_juegomemoria.setFont(new Font("Garamond", Font.BOLD, 40));
                    boton_juegomemoria.setText("¡BAILA CONMIGO!");
                }
                //Cuando sale el raton
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton_juegomemoria.setIcon(new ImageIcon("ui\\images\\bailando.png")); 
                    boton_juegomemoria.setBackground(Color.white);
                    boton_juegomemoria.setText(null);
                }
            });
        


        //Componentes panel norte 
        panel_norte.add(etiqueta_logo, BorderLayout.EAST); 
        panel_norte.add(titulo); 

        //Componentes panel central 
        panel_central.add(boton_juegocoordinacion);
        panel_central.add(boton_juegocartas);  
        panel_central.add(boton_juegomemoria);

        //Añadir panel 
        this.add(panel_norte,BorderLayout.NORTH); 
        this.add(panel_central, BorderLayout.CENTER); 

    }

}