package ui;
import domain.APP;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import util.Imagen;

public class Juego_Coordinacion extends JFrame implements Runnable {

    //Parametros del juego
    private boolean jugando=false; 
    private int nivel=1; 
    private int vidas= 5; 
    private JLabel etiqueta_nivel; 
    private JLabel etiqueta_vidas; 
    private Thread hilo;
    private JButton boton_inicio; 
    private JPanel panel; 


    //Parametros de la pelota que luego se convirtió en prenda (Son minijuegos no merece la pena crear clase ya que hay pocos parámtros)
    private final int pelota_ancho=210; 
    private final int pelota_alto=260;
    private int pelotaX=100; 
    private int pelotaY=115; 
    private int velocidadX=4; 
    private int velocidadY=15; 
    private boolean cayendo= false; 
    private Image prenda; 

    //Parametros del contenedor que luego se convirtió en la colada
    private Rectangle contenedor= new Rectangle (400,500,300,450); 
    private boolean movimiento_contendor= false; 
    private int velocidad_contenedor=3; 
    private Image imagenContenedor; 
 

    public Juego_Coordinacion(){
        super("Juego Coordinacion"); 
        this.setSize(900,900);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        //Componenentes necesarios de inicalizar antes porque se deben de pintar antes de inicializar los componentes
        imagenContenedor= (Imagen.escalarImagen("ui\\images\\cesta2.png", contenedor.width, contenedor.height)).getImage();
        prenda= (Imagen.escalarImagen("ui\\images\\prenda2.png", pelota_ancho,pelota_alto)).getImage();
        initComponents();
        this.setVisible(true);
    }

    public void initComponents(){

        //Panel con imagen de fondo 
        Image imagenFondo= (Imagen.escalarImagen("ui\\images\\fondo_coordinacion.jpg", getWidth(), getHeight())).getImage();
        panel = new JPanel() {
            @Override //Sobreescribimos el metodo del panel para pintar
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g); 
                g.drawImage(imagenFondo,0,0,getWidth(),getHeight(),this);
                dibujarJuego(g); 
            }
        }; 
        panel.setLayout(new GridBagLayout());
        panel.setFocusable(true); 
        panel.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e)
            {
                //Si estamos jugando y se presiona enter sin haberlo presionado antes, cae la pelota
                if (jugando==true && e.getKeyCode()==KeyEvent.VK_ENTER && cayendo==false)
                {
                    cayendo=true; 
                }
            }   
        });

        //Boton para iniciar el juego 
        ImageIcon f_boton_inicio= Imagen.escalarImagen("ui\\images\\comenzar.png", 300,100);
        boton_inicio= new JButton(f_boton_inicio); 
        boton_inicio.setPreferredSize(new Dimension(300,100));
        boton_inicio.setBackground(null);
        boton_inicio.setFocusPainted(false); //Para que no se selccione por defecto 
        boton_inicio.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) { 
                comenzarJuego(); 
            }
        }); 
        panel.add(boton_inicio); 
    
        //PANEL DE OPCIONES NORTE 

        //JPanel panel_norte=new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); 
        JPanel panel_norte=new JPanel(new BorderLayout()){
            @Override //Sobreescribimos el metodo del panel para pintar
            public void paintComponent(Graphics g)
            {   
                Image imagenpanel= (Imagen.escalarImagen("ui\\images\\panel.png", getWidth(), getHeight())).getImage();
                super.paintComponent(g); 
                g.drawImage(imagenpanel,0,0,getWidth(),getHeight(),this); 
            }
        }; 
            panel_norte.setBorder(new EmptyBorder(20, 50, 10, 50));
            panel_norte.setBackground(null);
            panel_norte.setPreferredSize(new Dimension(0,120));
        

        //Etiqueta nivel
        etiqueta_nivel=new JLabel("NIVEL "+nivel); 
        etiqueta_nivel.setForeground(Color.decode("#cb3572"));//Rosa oscuro el texto
        etiqueta_nivel.setFont(new Font("SansSerif", Font.BOLD, 30));
        etiqueta_nivel.setIcon(Imagen.escalarImagen("ui\\images\\cuadro.png", 200, 80));
        etiqueta_nivel.setHorizontalTextPosition(JLabel.CENTER);
        etiqueta_nivel.setVerticalTextPosition(JLabel.CENTER);

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

        //Componentes panel norte 
        panel_norte.add(inicio,BorderLayout.WEST);
        panel_norte.add(panel_norte_derecho,BorderLayout.CENTER);
        //panel_norte.add(f_nivel);
        // panel_norte.add(etiqueta_nivel,BorderLayout.CENTER);
        //panel_norte.add(etiqueta_vidas,BorderLayout.EAST);
        //panel_norte.add(f_vidas);

        this.add(panel,BorderLayout.CENTER); 
        this.add(panel_norte, BorderLayout.NORTH);
    }


    //Metodo para comenzar el juego 
    public void comenzarJuego()
    {
        boton_inicio.setVisible(false);
        jugando=true; 
        hilo= new Thread(this); 
        hilo.start(); 
        panel.requestFocusInWindow(); //Para que despues del boton que le ha quitado el foco se centre en la ventana 
    }


    //Método para dibujar contenedor y pelota 
    public void dibujarJuego(Graphics g){

        //Para dibujar la pelota 
        //g.setColor(Color.pink); 
        //g.fillOval(pelotaX, pelotaY,pelota_ancho,pelota_alto); 
        g.drawImage(prenda, pelotaX, pelotaY,pelota_ancho,pelota_alto,panel);

        //Dibujar Contenedor
        //g.setColor(Color.blue); 
        //g.fillRect(contenedor.x, contenedor.y, contenedor.width,contenedor.height); 
        g.drawImage(imagenContenedor, contenedor.x, contenedor.y, contenedor.width, contenedor.height, panel);

    }


    @Override 
    public void run(){
        while(true){
            try 
            {
                Thread.sleep(15); //controlamos la velocidad de los cambios
            
                //Controlar la pelota
                if (cayendo==false)
                {
                    pelotaX = pelotaX + velocidadX; 
                    
                    if (pelotaX <= 100 || pelotaX >= panel.getWidth()-pelota_ancho-100)
                    {
                        velocidadX= -velocidadX; 
                    }
                }
                else
                {
                    pelotaY= pelotaY + velocidadY; 
                    if (pelotaY+pelota_alto>= contenedor.y+contenedor.height-50)
                    {
                        if (contenedor.contains(pelotaX, pelotaY,pelota_ancho,pelota_alto))
                        {
                            Ventanas.mostrarventanaSiguienteNivel(this);
                            subirNivel();
                        }
                        else 
                        {
                            Ventanas.mostarventanaFallaste(this);
                            vidas=vidas-1;
                            etiqueta_vidas.setText(vidas+" VIDAS"); 
                            if (vidas==0)
                            {
                                Ventanas.mostrarVentanaPerdedor(this);
                            }
                        }
                        reiniciarPelota();
                    }
                }

                //Para mover el contenedor
                if (movimiento_contendor)
                {
                    contenedor.x = contenedor.x + velocidad_contenedor; 
                    if (contenedor.x <= 0 || contenedor.x >= panel.getWidth()-contenedor.width)
                    {
                        velocidad_contenedor = -velocidad_contenedor; 
                    }
                }
            
                panel.repaint();
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            } 

        }
    }

    //Reiniciar la pelota 
    public void reiniciarPelota()
    {
        if (velocidadX < 0) {
            velocidadX = -velocidadX; 
        }
        pelotaX=100; 
        pelotaY=115;  
        cayendo=false; 
    }

    //Para subir de nivel 
    public void subirNivel()
    {   
        if (velocidadX < 0) {
            velocidadX = -velocidadX; 
        }
        velocidadX = velocidadX+2; 
        nivel=nivel+1;
        etiqueta_nivel.setText("NIVEL " + nivel);
        if (nivel >=5)
        {
            movimiento_contendor=true; 
        }
        if (nivel<5)
        {
            //Para que la posicion del contenedor cambie se ha buscado como crear un numero aleatorio entre dos distancias)
            Random random= new Random();
            int max= panel.getWidth()-2*contenedor.width;
            int min= 2*contenedor.width;
            contenedor.x= random.nextInt((max - min) + 1) + min;
        }
        if (nivel>8)
        {
            etiqueta_nivel.setText("FIN");
            Ventanas.mostrarVentanaGanador(this);
        }

    }   

}