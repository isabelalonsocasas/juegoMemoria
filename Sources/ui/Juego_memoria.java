package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.Clip;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.APP;
import util.Imagen;
import util.Sonido;

public class Juego_memoria extends JFrame implements Runnable {

    private JPanel panel;
    private ArrayList<JButton> todosLosBotones; 
    private ArrayList<JButton> secuencia; 
    private ArrayList<JButton> secuenciaCorrecta; 
    private ArrayList<JButton> secuenciaJugador; 
    private JLabel etiqueta_nivel; 
    private int nivel=1; 
    private JLabel etiqueta_vidas; 
    private int vidas=10; 
    private boolean jugadorPuedePulsar=false; 
    private Clip musicafondo; 

    public Juego_memoria()
    {
        super("Juego memoria"); 
        this.setSize(900,900);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Inicializacion de algunos componentes 
        todosLosBotones = new ArrayList<>();
        secuenciaCorrecta = new ArrayList<>();
        secuenciaJugador = new ArrayList<>();
        secuencia= new ArrayList<>(); 
        initComponents();
        this.setVisible(true);

    }

    public void initComponents()
    {
        //Panel 
        panel = new JPanel(); 
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(5, 4, 0,0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        //Botones 
        for (int i = 0; i < 20; i++) {
            JButton boton = new JButton();
            boton.setBackground(Color.white); //Al principio se ponen en gris
            boton.setFocusPainted(false);
            boton.setOpaque(true);
            boton.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed (ActionEvent e)
                {
                    turnodelJugador(boton); 
                }   
            });
            todosLosBotones.add(boton);
            panel.add(boton);
        }

        //PANEL NORTE
        JPanel panel_norte=new JPanel(new BorderLayout()){
            @Override //Sobreescribimos el metodo del panel para pintar
            public void paintComponent(Graphics g)
            {   
                Image imagenpanel= (Imagen.escalarImagen("ui\\images\\panel3.png", getWidth(), getHeight())).getImage();
                super.paintComponent(g); 
                g.drawImage(imagenpanel,0,0,getWidth(),getHeight(),this); 
            }
        }; 
            panel_norte.setBorder(new EmptyBorder(20, 50, 10, 50));
            panel_norte.setBackground(null);
            panel_norte.setPreferredSize(new Dimension(0,120));

        //Boton volver al inicio
        ImageIcon volver_inicio= Imagen.escalarImagen("ui\\images\\inicio.png", 90, 90);
        JButton inicio=new JButton(volver_inicio); 
            inicio.setOpaque(false);
            inicio.setContentAreaFilled(false);
            inicio.setBorderPainted(false);//Para que no se seleccione por defecto
            inicio.setFocusPainted(false);
            //inicio.setBackground(Color.white);
            //inicio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        JFrame pestana= this;
        inicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                musicafondo.stop();
                pestana.dispose();
                APP app= new APP(); 
            }
        });
        //Etiqueta nivel
        etiqueta_nivel=new JLabel("NIVEL "+nivel); 
        etiqueta_nivel.setForeground(Color.decode("#cb3572"));//Rosa oscuro el texto
        etiqueta_nivel.setFont(new Font("SansSerif", Font.BOLD, 30));
        etiqueta_nivel.setIcon(Imagen.escalarImagen("ui\\images\\cuadro.png", 200, 80));
        etiqueta_nivel.setHorizontalTextPosition(JLabel.CENTER);
        etiqueta_nivel.setVerticalTextPosition(JLabel.CENTER);

        //Etiqueta vidas
        etiqueta_vidas=new JLabel(vidas+" VIDAS"); 
        etiqueta_vidas.setForeground(Color.decode("#cb3572"));
        etiqueta_vidas.setFont(new Font("SansSerif", Font.BOLD, 30));
        etiqueta_vidas.setIcon(Imagen.escalarImagen("ui\\images\\cuadro.png", 200, 80));
        etiqueta_vidas.setHorizontalTextPosition(JLabel.CENTER);
        etiqueta_vidas.setVerticalTextPosition(JLabel.CENTER);

        //Para organizar el panel norte 
        JPanel panel_norte_derecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
            panel_norte_derecho.setOpaque(false); // fondo del mismo color
            panel_norte_derecho.add(etiqueta_vidas);
            panel_norte_derecho.add(etiqueta_nivel);


        panel_norte.add(inicio,BorderLayout.WEST);
        panel_norte.add(panel_norte_derecho,BorderLayout.CENTER);

        this.add(panel,BorderLayout.CENTER); 
        this.add(panel_norte,BorderLayout.NORTH);

        //Para lanzar el hilo del juego 
        nuevoNivel(); 
    }

    private void generarSecuencia() 
    {
        secuencia = new ArrayList<>(todosLosBotones);
        Collections.shuffle(secuencia);
    }


    public void nuevoNivel()
    {
        etiqueta_nivel.setText("NIVEL "+nivel);
        if (nivel==1)
        {
            generarSecuencia();
        }
        musicafondo = Sonido.reproducirEnBucle("util\\sonido_fondo.wav");
        jugadorPuedePulsar=false; 
        for (JButton boton : secuenciaJugador) {
            boton.setBackground(Color.WHITE);
        }
        secuenciaCorrecta.clear(); 
        secuenciaJugador.clear(); 

        if (secuencia!= null)
        {
            secuenciaCorrecta = new ArrayList<>(secuencia.subList(0, nivel)); //Nueva secuencia del tamaño del nivel 
        }

        //Iniciamos el hilo principal 
        new Thread(this).start(); 
    }

    public void turnodelJugador(JButton boton)
    {
        if(jugadorPuedePulsar==false)
        {
            return; //No hace nada cuando pulsas algun boton 
        }
        else 
        {
            secuenciaJugador.add(boton); 
            boton.setBackground(Color.green);
        }

        if (secuenciaJugador.size()==secuenciaCorrecta.size())
        {
            jugadorPuedePulsar=false; //No se pulsa mas 

            //Esperamos para que se vea la secuencia entera antes de comprobar
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        comprobarResultado(); //Metodo para comprobar el resultado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start(); 
        }
    }

    public void comprobarResultado()
    {
         int correcto= 0; //variable dentro del método que comprueba si el jugador ha realizado bien la secuencia
        //Comprobamos 
        for (int i = 0; i < secuenciaCorrecta.size(); i++) 
        {
            if (secuenciaJugador.get(i) != secuenciaCorrecta.get(i)) 
            {
                secuenciaJugador.get(i).setBackground(Color.red);
                correcto = correcto+1;
            }
        }

        //Posibilidades despues de comprobar 
        if (correcto==0)
        {
            if (nivel==20)
            {   
                //Esto ya lo hace nuestra clase Ventanas
                //JOptionPane.showMessageDialog(this, "¡HAS GANADO! Has completado los 20 niveles");
                //this.dispose(); 
                musicafondo.stop();
                Ventanas.mostrarVentanaGanador(this);
                //APP app1= new APP(); 
            }
            else 
            {
                musicafondo.stop(); 
                Sonido.reproducir("util\\si.wav");
                //JOptionPane.showMessageDialog(this, "¡Correcto! Nivel " + nivel + " superado");
                Ventanas.mostrarventanaSiguienteNivel(this);
                nivel++;
                nuevoNivel();
            }
        }
        else 
        {
            vidas=vidas-1; 
            etiqueta_vidas.setText(vidas+" VIDAS");
            if (vidas <= 0)
            {
                //JOptionPane.showMessageDialog(this, "Te has quedado sin vidas. Fin del juego.");
                //this.dispose(); 
                //APP app2= new APP();
                Ventanas.mostrarVentanaPerdedor(this);
            }
            else
            {
                musicafondo.stop();
                Sonido.reproducir("util\\no.wav");
                //JOptionPane.showMessageDialog(this, "Has fallado. Te quedan " + vidas + " vidas.");
                Ventanas.mostarventanaFallaste(this);
                nuevoNivel();
            }
        }
    }

    //Hilo principal que muestre la secuencia 
    @Override
    public void run()
    {
        try
        {   //Para cada boton se ilumina unos segundos se apaga se espera y se vuelve a iluminar 
            for (JButton boton : secuenciaCorrecta) {
                Thread.sleep(1000);
                boton.setBackground(Color.pink);
                Thread.sleep(1000); 
                boton.setBackground(Color.WHITE); 
            }
            jugadorPuedePulsar=true; 
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}