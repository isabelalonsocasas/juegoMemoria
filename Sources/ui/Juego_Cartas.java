package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.APP;
import util.Imagen; 

public class Juego_Cartas extends JFrame implements Runnable{

    private static final int num_cartas= 16; 
    private static final int num_parejas=num_cartas/2; 

    private Map< JButton, ArrayList<Object>> cartas;
    private ArrayList<JButton> cartasVolteadas;
    private int acertadas=0; 

    //Imagen de las cartas del revés ajustada
    private static ImageIcon reverso; 

    public Juego_Cartas() {
        super("Juego cartas"); 
        this.setSize(900,900);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //Inicialización de algunos componentes necesarios
        reverso= Imagen.escalarImagen("ui\\images\\logotipo.png", 200, 100);
        cartasVolteadas=new ArrayList<>();
        cartas=new HashMap<>();
        this.setLayout(new BorderLayout());
        initComponents();
        this.setVisible(true);
    }


    public void initComponents(){
        //Panel 
        JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 4, 10,10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Datos de las cartas, se debe hacer así porque cada una tiene un tamaño segun los pixeles de cada imagen
        String ruta="ui\\images\\";
        String[] nombres= {ruta+"carta0.jpg",ruta+"carta1.jpg",ruta+"carta2.jpg",ruta+"carta3.jpg",ruta+"carta4.jpg",ruta+"carta5.jpg",ruta+"carta6.jpg",ruta+"carta7.jpg"}; 
        int[][] tamanos= {{450,250},{400,350},{500,250},{450,250},{400,250},{450,350},{450,250},{450,250}};

        //Pares de cartas con imagen y teto para comparar 
        ArrayList<Object[]> paresCarta = new ArrayList<>();
        for (int i = 0; i < num_parejas ; i++) {
            String texto = "carta " + i;
            ImageIcon imagen = Imagen.escalarImagen(nombres[i], tamanos[i][0], tamanos[i][1]);
            paresCarta.add(new Object[]{texto, imagen});
            paresCarta.add(new Object[]{texto, imagen});
        }
        Collections.shuffle(paresCarta);

        //Creamos los botones de las cartas
        for (int i = 0; i < num_cartas; i++) {
            JButton carta = new JButton();
            carta.setIcon(reverso);
            carta.setBackground(Color.pink);
            carta.setBorder(BorderFactory.createLineBorder(Color.blue, 3));

            //Sacamos los datos de la carta que vamos a añadir al botón 
            Object[] datosCarta = paresCarta.get(i);
            String texto = (String) datosCarta[0];
            ImageIcon imagen = (ImageIcon) datosCarta[1];

            //Recopilamos los datos 
            ArrayList<Object> datos = new ArrayList<>();
            datos.add(texto);
            datos.add(imagen);
            datos.add(false); // no esta volteada

            //Los añadimos a la variable global para tener cada carta asociada a cada botón
            cartas.put(carta, datos);

            //Necesario porque dentro de una clase anonima no coge variables que cambien, asi que debemos definirlo como final
            final JButton carta_fija = carta;
            final Juego_Cartas juego = this;

            //Le añadimos a cada carta su estructura de voltearse
            carta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //Comprobar que no esten giradas ya y que no haya mas de dos giradas
                    if (cartasVolteadas.size() < 2 && (boolean)cartas.get(carta_fija).get(2)==false) {
                        girar(carta_fija);
                        cartasVolteadas.add(carta_fija);

                    if (cartasVolteadas.size() == 2) {
                        Thread hilo = new Thread(juego);
                        hilo.start();
                        }
                    }
                }
            });

            panel.add(carta);
        }

        //PANEL NORTE CON LA OPCION DE VOLVER AL INICIO
        JPanel panel_norte=new JPanel(new BorderLayout()){
            @Override //Sobreescribimos el metodo del panel para pintar
            public void paintComponent(Graphics g)
            {   
                Image imagenpanel= (Imagen.escalarImagen("ui\\images\\panel2.png", getWidth(), getHeight())).getImage();
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
            inicio.setBorderPainted(false);//Para que no se selccione por defecto
            inicio.setFocusPainted(false);
            //inicio.setBackground(Color.white);
            //inicio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        JFrame pestana= this;
        inicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pestana.dispose();
                APP app= new APP(); 
            }
        });
        panel_norte.add(inicio,BorderLayout.WEST);

        this.add(panel_norte, BorderLayout.NORTH); 
        this.add(panel,BorderLayout.CENTER);
    }


    //Hilo para controlar el juego 
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JButton c1 = cartasVolteadas.get(0);
        JButton c2 = cartasVolteadas.get(1);

        String t1 = (String) cartas.get(c1).get(0);
        String t2 = (String) cartas.get(c2).get(0);

        if (t1.equals(t2)) {
            //Bloqueamos los botones si se han acertado
            c1.setEnabled(false);
            c2.setEnabled(false);
            acertadas += 2;

            if (acertadas == num_cartas) {
                Ventanas.mostrarVentanaGanador(this);
                //Esto ya lo hace nuestra clase Ventanas
                //this.dispose();
                //APP app= new APP(); 
            }
        } else {
            //Como vamos a cambiar parametros de los botones en el run para que no haya error es necesario invokeLater
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run(){
                    ocultar(c1);
                    ocultar(c2);
                }
            });
        }

        cartasVolteadas.clear();
    }

    public void girar(JButton carta) {
        ArrayList<Object> datos = cartas.get(carta);
        carta.setIcon((ImageIcon) datos.get(1));
        cartas.get(carta).set(2, true);
    }

    public void ocultar(JButton carta) {
        carta.setIcon(reverso);
        cartas.get(carta).set(2, false); 
    }
}



