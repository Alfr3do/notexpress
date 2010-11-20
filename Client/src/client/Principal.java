package client;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Rectangle;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;

// Importamos la librería de OpenGL
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.nio.FloatBuffer;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JTextField;
import javax.swing.event.MenuKeyListener;

import oracle.jdeveloper.layout.VerticalFlowLayout;

public class Principal extends Debug implements GLEventListener, MouseListener, MouseMotionListener,
                                          MouseWheelListener
{
    JPanel panelDibujo;
    Container contenedor;
    transient Toolkit kit;
    Dimension dimensionPantalla;
    int altura;
    int anchura;

    static GL2 gl;

    static GLCanvas canvas;
    private TextRenderer renderer;
    private transient EstrategiaDibujo estrategia;
    private Boolean manoAlzada;
    private Boolean escribiendoTexto;
    private ArrayList<Point> temporal, datos;
    private ArrayList<Point> textosPos;
  private ArrayList<String> textos;
    
    
    //static CanvasMouseEvents canvas;
    private JPanel jPanel1 = new JPanel();
    private JPanel lienzo = new JPanel();
    private JPanel controles = new JPanel();
    
    private JButton jButton1 = new JButton("",new ImageIcon("resources/lapiz.png"));
    private JButton jButton2 = new JButton("",new ImageIcon("resources/linea.png"));
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
     
    private JButton jButton3 = new JButton("",new ImageIcon("resources/rectangulo.png"));
    private JButton jButton4 = new JButton("",new ImageIcon("resources/borrador.png"));
    private JButton jButton5 = new JButton("",new ImageIcon("resources/circulo.png"));
    private JButton jButton6 = new JButton("",new ImageIcon("resources/elipse.png"));
    private JButton jButton7 = new JButton("",new ImageIcon("resources/texto.png"));
    
    private JButton jButtonAbrir = new JButton("", new ImageIcon("resources/abrir.png"));
    private JButton jButtonGuardar = new JButton("", new ImageIcon("resources/guardar.png"));
    private JButton jButtonNuevo = new JButton("", new ImageIcon("resources/nuevo.png"));
    private JButton jButtonImprimir = new JButton("", new ImageIcon("resources/imprimir.png"));
    private JButton jButtonSalir = new JButton("", new ImageIcon("resources/salir.png"));
    
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    
    private JMenuItem menuFileNuevo = new JMenuItem();
    private JMenuItem menuFileAbrir = new JMenuItem();
    private JMenuItem menuFileGuardar = new JMenuItem();
    private JMenuItem menuFileImprimir = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    
    private JTextField textField = new JTextField("");
    
    private JLabel statusBar = new JLabel();

    // Constructor
public Principal()
{
    super("Principal");
    setDebug(true);
    
    temporal = new ArrayList<Point>();
    datos = new ArrayList<Point>();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    //set estrategia Por defecto        
    setManoAlzada(false);
    setEscribiendoTexto(false);
    estrategia = new EstrategiaLinea();
    jButton2.setEnabled(false);
}


public static void main(String[] args)
{
    new Principal();
}

    public void init(GLAutoDrawable drawable)
    {
        gl = drawable.getGL().getGL2();
        imprimir (gl.glGetString(GL.GL_VERSION));
        imprimir (gl.glGetString(GL.GL_VENDOR));
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        renderer = new TextRenderer(new Font("Arial", Font.BOLD, 14));
        
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        gl = drawable.getGL().getGL2();
        //gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glMatrixMode(GL.GL_VIEWPORT);
        gl.glLoadIdentity();

        gl.glOrtho(0, anchura, 0, altura, -1.0, 1.0);

        this.repaint();
    }

    public void evalCoordHermite(double t, int indice) {
    // evaluate the coordinates and specify the points
    double x, y, z, t_1, t2, t_2, t3, t_3;
    t_1 = 1-t;
    t2 = t*t;
    t_2 = t_1*t_1;
    t3 = t2*t;
    t_3 = t_2*t_1;
    x = t_3*datos.get(indice).x+3*t*t_2*datos.get(indice+1).x
    +3*t2*t_1*datos.get(indice+2).x+t3*datos.get(indice+3).x;
    y = t_3*datos.get(indice).y+3*t*t_2*datos.get(indice+1).y
    +3*t2*t_1*datos.get(indice+2).y+t3*datos.get(indice+3).y;
    z = 1.0;
    gl.glVertex3d(x, y, z);
    }
    public void display(GLAutoDrawable drawable)
    {
        
        int i;
        gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        

        //dibujar temporales
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        //gl.glPointSize(0.5f);
        gl.glBegin(GL.GL_POINTS);
        gl.glPointSize(15.0f);
      gl.glLineWidth(5.0f);
            //gl.glVertex2i(anchura/2, altura/2);
            //gl.glVertex3d(10.0d, 10.0d, 1.0d);
        if (temporal.size() > 0)
            for (i =0;i<temporal.size();i++)
            {
                gl.glVertex3d(temporal.get(i).x,temporal.get(i).y,1.0d);
            }
        gl.glEnd();

        
        gl.glColor3f(0.0f, 0.0f, 0.0f);
      //  gl.glLineWidth(1);
        
            //gl.glVertex2i(anchura/2, altura/2);
            //gl.glVertex3d(10.0d, 10.0d, 1.0d);
        //Dibujando Datos.
        if (datos.size() > 0)
            for (i =0;i<datos.size();i+=4)
            {
                gl.glLineWidth(5.0f);
                gl.glBegin(GL.GL_LINE_STRIP);
                 
                //gl.glVertex3d(datos.get(i).x,datos.get(i).y,1.0d);
                for (int j = 0; j<=30; j++)
                    evalCoordHermite(j/30.0,i);
                gl.glEnd();
            }
        
        
        if (textosPos != null && textosPos.size() > 0) {
            imprimir("agregando Texto");
          
          renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
          
              // optionally set the color
          renderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
          int j;
          for (j = 0; j < textosPos.size(); j++)
          {
          if (textos.size()>j){
              imprimir("deberia salir "+textos.get(j));
              renderer.draw(textos.get(j), textosPos.get(j).x * drawable.getWidth() /anchura ,
                                            textosPos.get(j).y*drawable.getHeight()/altura);
          }
          else
              {
                
              }
          }
              // ... more draw commands, color changes, etc.
          renderer.endRendering();
        }
        
        /*gl.glPointSize(6.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex3d(ctrlp[0][0],ctrlp[0][1],ctrlp[0][2]);
        gl.glVertex3d(ctrlp[3][0],ctrlp[3][1],ctrlp[3][2]);
        gl.glEnd();*/
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
    {
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
    }



    private void jbInit() throws Exception {

        kit = Toolkit.getDefaultToolkit();
        dimensionPantalla = kit.getScreenSize();
        altura = (int)dimensionPantalla.getHeight();
        anchura = (int)dimensionPantalla.getWidth();

        //debugin
        //altura = 500;
        //anchura = 400;
        
        //this.setSize(new Dimension(176, 147));
        
        //this.setJMenuBar( menuBar );
        //menuFile.setText( "Archivo" );
        //menuFile.setMnemonic(KeyEvent.VK_A);
        //menuFileAbrir.setText( "Abrir" );
        //menuFileAbrir.setMnemonic(KeyEvent.VK_O);
        //menuFileGuardar.setText("Guardar");
        //menuFileGuardar.setMnemonic(KeyEvent.VK_G);
        //menuFileNuevo.setText("Nuevo");
        //menuFileImprimir.setText("Imprimir");
        //menuFileImprimir.setMnemonic(KeyEvent.VK_P);
        //menuFileExit.setText( "Salir" );
        //menuFileExit.setMnemonic(KeyEvent.ALT_DOWN_MASK+KeyEvent.VK_X);
        //menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); }} );
        statusBar.setText( "Iniciando" );
        //menuFile.add(menuFileNuevo);
        //menuFile.add(menuFileAbrir);
        //menuFile.add(menuFileGuardar);
        //menuFile.add(menuFileImprimir);
        //menuFile.add( menuFileExit );
        //menuBar.add( menuFile );
        this.getContentPane().add( statusBar, BorderLayout.SOUTH );
        this.getContentPane().add(lienzo, null);
        lienzo.setBounds(new Rectangle(50, 0, anchura-50, altura-80));
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(altura, anchura));
        jPanel1.setBounds(new Rectangle(0, 0, 50, altura));
        jPanel1.setLayout(verticalFlowLayout1);
        
        
        jButton1.setPreferredSize(new Dimension(35, 35));
        jButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              setEstrategiaManoAlzada(e);
            }
            });
        jButton2.setPreferredSize(new Dimension(35, 35));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setEstrategiaLinea(e);
                }
            });
        jButton3.setPreferredSize(new Dimension(35, 35));
        jButton3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setEstrategiaRectangulo(e);
                }
            });
        jButton4.setPreferredSize(new Dimension(35, 35));
        jButton4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setEstrategiaBorrar(e);
                }
            });
        jButton5.setPreferredSize(new Dimension(35, 35));
        jButton5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setEstrategiaCirculo(e);
                }
            });
        jButton6.setPreferredSize(new Dimension(35, 35));
        jButton6.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  setEstrategiaElipse(e);
              }
            });
        jButton7.setPreferredSize(new Dimension(35, 35));
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEstrategiaTexto(e);
            }               
            });
        
        jPanel1.add(jButton2, null);
        jPanel1.add(jButton1, null);
        jPanel1.add(jButton3, null);
        jPanel1.add(jButton4, null);
        jPanel1.add(jButton5, null);
        jPanel1.add(jButton6, null);
        jPanel1.add(jButton7, null);
        
       // jPanel1.add(textField);
        this.getContentPane( ).add(jPanel1, null);
        
        
        jButtonAbrir.setPreferredSize(new Dimension(35, 35));
        jButtonAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEstrategiaTexto(e);
            }               
            });
      jButtonGuardar.setPreferredSize(new Dimension(35, 35));
      jButtonGuardar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              setEstrategiaTexto(e);
          }               
          });        
      jButtonNuevo.setPreferredSize(new Dimension(35, 35));
      jButtonNuevo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              setEstrategiaTexto(e);
          }               
          });
      jButtonImprimir.setPreferredSize(new Dimension(35, 35));
      jButtonImprimir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              setEstrategiaTexto(e);
          }               
          });
      jButtonSalir.setPreferredSize(new Dimension(35, 35));
      jButtonSalir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              fileExit_ActionPerformed(e);
          }               
          });
        controles.setBounds(new Rectangle(50, altura-80, anchura - 50, 80));
        controles.setLayout(null);
        int anchoBoton = 50;
        int despl = - anchoBoton - 10 - anchoBoton -10 -anchoBoton -10 -anchoBoton ;
         
        jButtonAbrir.setBounds((anchura - 50)/2 + despl , 5, anchoBoton, 35);
        despl += 10 + anchoBoton;
        jButtonGuardar.setBounds((anchura - 50)/2 + despl , 5, anchoBoton, 35);
        despl += 10 + anchoBoton;
        jButtonNuevo.setBounds((anchura - 50)/2 + despl , 5, anchoBoton, 35);
        despl += 10 + anchoBoton;
        jButtonImprimir.setBounds((anchura - 50)/2 + despl , 5, anchoBoton, 35);
        despl += 10 + anchoBoton;
        jButtonSalir.setBounds((anchura - 50)/2 + despl , 5, anchoBoton, 35);
        
        textField.setBounds(0,5, 200 , 45);
        textField.setEnabled(false);
        textField.addKeyListener(new KeyAdapter(){
          public void keyPressed(KeyEvent evt){
              char ch = evt.getKeyChar();
             if (textos.size() == textosPos.size()) {
               textos.set(textosPos.size() - 1,textField.getText()+ch);

             }
             else {
               while (textos.size() < textosPos.size())
                  textos.add("");
               textos.set( textosPos.size() -1,textField.getText()+ch);
             }
             canvas.repaint();
            canvas.repaint();
          }
          }); 
        
        controles.add(jButtonAbrir, null);
        controles.add(jButtonGuardar, null);
        controles.add(jButtonNuevo, null);
        controles.add(jButtonImprimir, null);
        controles.add(jButtonSalir, null);
        controles.add(textField);
        
        this.getContentPane().add(controles,null);
        //GL STUFFS

        //GLProfile glp = GLProfile.getDefault();
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);
        
        canvas = new GLCanvas(caps);
        //canvas = new CanvasMouseEvents(caps);
        

        //Events Listeners
        canvas.addGLEventListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        //gl = canvas.getGL().getGL2();


        lienzo.add(canvas,null);
        canvas.setBounds(0, 0, anchura-80, altura-80);
        imprimir("anx"+anchura+"x"+altura);

        this.setSize(anchura, altura);
        //this.setLocation(anchura/4, altura/4);
        this.setLocation(0, 0);
        this.setResizable(false);
        this.setVisible(true);
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    private void fileExit_ActionPerformed(ActionEvent ae) {
        System.exit(0);
    }


    public void mouseDragged(MouseEvent e) {
        //imprimir("mouse dragged");
        if (isManoAlzada())
        {
          if (estrategia.agregarPunto(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                (int)( (altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())))) {
              imprimir("buscar los puntos a agregar");
              temporal.clear();
              for (Point punto : estrategia.obtenerDatos()) {
                  datos.add(punto);                 
              }
              
          }
          else {
              temporal.add(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                (int)((altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())));
          }
          canvas.repaint();
          ((EstrategiaManoAlzada)(estrategia)).setContinuo(true);
        }        
        //canvas.repaint();
        
    }

    public void mouseMoved(MouseEvent e) {
        //imprimir("mouse moved");
    }
    
    public void mouseClicked(MouseEvent e) {
        //imprimir("click");
        
    }

    public void mousePressed(MouseEvent e) {
        imprimir("pressed");
    }

    public void mouseReleased(MouseEvent e) {
        //imprimir("released");
        //imprimir(e.getPoint().x+","+e.getPoint().y);
        //imprimir(canvas.getBounds().getHeight());
        if (!isManoAlzada() && !getEscribiendoTexto())
        {
            if (estrategia.agregarPunto(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                  (int)( (altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())))) {
                
                temporal.clear();
                for (Point punto : estrategia.obtenerDatos()) {
                    datos.add(punto);
                }
                estrategia.borrarDatos();
            }
            else {
                temporal.add(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                  (int)((altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())));
            }
            
        }
        else if (isManoAlzada()) {
            imprimir("finalizando la continuidad");
          ((EstrategiaManoAlzada)(estrategia)).setContinuo(false);
            estrategia.borrarDatos();
            temporal.clear();
        }
        if (getEscribiendoTexto())
        {
              if (textos  == null)
              {
                  textos = new ArrayList<String>();
                  textosPos = new ArrayList<Point>();
              }
              
             temporal.add(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                            (int)((altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())));
              
              Point esquina = new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                  (int)( (altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight()));
              
              //Point esquina = new Point(e.getPoint().x, e.getPoint().y);
              textosPos.add(esquina);
              
        }
        
      canvas.repaint();
    }

    public void mouseEntered(MouseEvent e) {
       // imprimir("entered");
    }

    public void mouseExited(MouseEvent e) {
        //imprimir("exited");
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        imprimir("wheeled");
    }

    public void setManoAlzada(Boolean isManoAlzada) {
        this.manoAlzada = isManoAlzada;
    }

    public Boolean isManoAlzada() {
        return manoAlzada;
    }
    private void activarBotones() {
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        
       //limpia los temporales
        
        temporal.clear();
        canvas.repaint();
    }
    private void setEstrategiaLinea(ActionEvent e) {
        setManoAlzada(false);
        setEscribiendoTexto(false);
        estrategia = new EstrategiaLinea();
        activarBotones();
        jButton2.setEnabled(false);
    }

  private void setEstrategiaManoAlzada(ActionEvent e) {
        setManoAlzada(true);
        setEscribiendoTexto(false);
        activarBotones();
        jButton1.setEnabled(false);
        estrategia = new EstrategiaManoAlzada(true);
        imprimir("mano alzada");
  }


    private void setEstrategiaRectangulo(ActionEvent e) {
        setManoAlzada(false);
        estrategia = new EstrategiaCuadrado();
        activarBotones();
        jButton3.setEnabled(false);
    }

    private void setEstrategiaCirculo(ActionEvent e) {
        setManoAlzada(false);
      setEscribiendoTexto(false);
        estrategia = new EstrategiaCirculo();
        activarBotones();
        jButton5.setEnabled(false);
    }

    private void setEstrategiaBorrar(ActionEvent e) {
        estrategia.borrarDatos();
        temporal.clear();
        datos.clear();
        if (textos != null)
          textos.clear();
        if (textosPos != null)
          textosPos.clear();
        canvas.repaint();
    }
    private void setEstrategiaElipse(ActionEvent e) {
        setManoAlzada(false);
        setEscribiendoTexto(false);
        estrategia = new EstrategiaEclipse();
        activarBotones();
        jButton6.setEnabled(false);
    }
    private void setEstrategiaTexto(ActionEvent e) {
     
      setManoAlzada(false);
      setEscribiendoTexto(true);
      estrategia = new EstrategiaTexto();
      activarBotones();
      jButton7.setEnabled(false);
    }

    public void setEscribiendoTexto(Boolean escribiendoTexto) {
        this.escribiendoTexto = escribiendoTexto;
        textField.setEnabled(escribiendoTexto);
    }

    public Boolean getEscribiendoTexto() {
        return escribiendoTexto;
    }
}
