package client;

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
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.nio.FloatBuffer;


import java.util.ArrayList;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
    private transient EstrategiaDibujo estrategia;
    private Boolean manoAlzada;
    private ArrayList<Point> temporal, datos;
    
    //static CanvasMouseEvents canvas;
    private JPanel jPanel1 = new JPanel();
    private JPanel lienzo = new JPanel();
    private JButton jButton1 = new JButton("",new ImageIcon("resources/lapiz.png"));
    private JButton jButton2 = new JButton("",new ImageIcon("resources/linea.png"));
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JButton jButton3 = new JButton("",new ImageIcon("resources/rectangulo.png"));
    private JButton jButton4 = new JButton("",new ImageIcon("resources/borrador.png"));
    private JButton jButton5 = new JButton("",new ImageIcon("resources/circulo.png"));
    private JButton jButton6 = new JButton("",new ImageIcon("resources/elipse.png"));
    private JButton jButton7 = new JButton("",new ImageIcon("resources/texto.png"));
    
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    
    private JMenuItem menuFileNuevo = new JMenuItem();
    private JMenuItem menuFileAbrir = new JMenuItem();
    private JMenuItem menuFileGuardar = new JMenuItem();
    private JMenuItem menuFileImprimir = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    
    private JLabel statusBar = new JLabel();
    
    /*double ctrlp[][] = { {-0.5, -0.5, -0.5}, {-1.0, 1.0, 1.0},
    {1.0, -1.0, 1.0}, {0.5, 0.5, 1.0}
    };*/
    
    //curve
    /*double ctrlp[][][] = { {{100.0, 100.0, 1.0}, {50, 150, 1.0}
                    , {250.0, 150.0, 1.0}, {200.0, 200.0, 1.0}}
        };*/
    
    //circle arc
    /*double ctrlp[][][] = { {{250.0, 200.0, 1.0}, {250, 250, 1.0}
                        , {190.0, 250.0, 1.0}, {200.0, 250.0, 1.0}}
            };*/
    
    /*double ctrlp[][][] = { {{250.0, 200.0, 1.0}, {250, 250, 1.0}
                        , {190.0, 250.0, 1.0}, {200.0, 250.0, 1.0}},
                           
                            {{200.0, 250.0, 1.0}, {150, 250, 1.0}
                            , {150.0, 190.0, 1.0}, {150.0, 200.0, 1.0}},
                           
                            {{150.0, 200.0, 1.0}, {150, 150, 1.0}
                            , {210.0, 150.0, 1.0}, {200.0, 150.0, 1.0}},
                           
                           {{200.0, 150.0, 1.0},{250.0, 150.0, 1.0}, 
                            {250, 210, 1.0}, {250.0, 200.0, 1.0}
                            }
            };*/

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
        gl.glPointSize(3.0f);
        gl.glBegin(GL.GL_POINTS);
            //gl.glVertex2i(anchura/2, altura/2);
            //gl.glVertex3d(10.0d, 10.0d, 1.0d);
        if (temporal.size() > 0)
            for (i =0;i<temporal.size();i++)
            {
                gl.glVertex3d(temporal.get(i).x,temporal.get(i).y,1.0d);
            }
        gl.glEnd();

        
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glLineWidth(1);
        
            //gl.glVertex2i(anchura/2, altura/2);
            //gl.glVertex3d(10.0d, 10.0d, 1.0d);
        //Dibujando Datos.
        if (datos.size() > 0)
            for (i =0;i<datos.size();i+=4)
            {
                gl.glBegin(GL.GL_LINE_STRIP);
                //gl.glVertex3d(datos.get(i).x,datos.get(i).y,1.0d);
                for (int j = 0; j<=30; j++)
                    evalCoordHermite(j/30.0,i);
                gl.glEnd();
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
        
        this.setJMenuBar( menuBar );
        menuFile.setText( "Archivo" );
        menuFile.setMnemonic(KeyEvent.VK_A);
        menuFileAbrir.setText( "Abrir" );
        menuFileAbrir.setMnemonic(KeyEvent.VK_O);
        menuFileGuardar.setText("Guardar");
        menuFileGuardar.setMnemonic(KeyEvent.VK_G);
        menuFileNuevo.setText("Nuevo");
        menuFileImprimir.setText("Imprimir");
        menuFileImprimir.setMnemonic(KeyEvent.VK_P);
        menuFileExit.setText( "Salir" );
        menuFileExit.setMnemonic(KeyEvent.ALT_DOWN_MASK+KeyEvent.VK_X);
        menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); }} );
        statusBar.setText( "Iniciando" );
        menuFile.add(menuFileNuevo);
        menuFile.add(menuFileAbrir);
        menuFile.add(menuFileGuardar);
        menuFile.add(menuFileImprimir);
        menuFile.add( menuFileExit );
        menuBar.add( menuFile );
        this.getContentPane().add( statusBar, BorderLayout.SOUTH );
        this.getContentPane().add(lienzo, null);
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(altura, anchura));
        jPanel1.setBounds(new Rectangle(0, 0, 50, altura));
        jPanel1.setLayout(verticalFlowLayout1);
        lienzo.setBounds(new Rectangle(50, 0, anchura-50, altura));
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
        jButton7.setPreferredSize(new Dimension(35, 35));
        jPanel1.add(jButton2, null);
        jPanel1.add(jButton1, null);
        jPanel1.add(jButton3, null);
        jPanel1.add(jButton4, null);
        jPanel1.add(jButton5, null);
        jPanel1.add(jButton6, null);
        jPanel1.add(jButton7, null);
        this.getContentPane().add(jPanel1, null);

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
        imprimir("mouse dragged");
        if (isManoAlzada())
        {
          
          if (estrategia.agregarPunto(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                (int)( (altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())))) {
              
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
        if (!isManoAlzada())
        {
            if (estrategia.agregarPunto(new Point((int)((anchura)*e.getPoint().x/canvas.getBounds().getWidth()),
                                                  (int)( (altura) * (canvas.getBounds().getHeight()-e.getPoint().y) / canvas.getBounds().getHeight())))) {
                imprimir("crearLinea");
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
        }
        else {
          
        }
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
        estrategia = new EstrategiaLinea();
        activarBotones();
        jButton2.setEnabled(false);
    }

  private void setEstrategiaManoAlzada(ActionEvent e) {
        setManoAlzada(true);
        activarBotones();
        jButton1.setEnabled(false);
        estrategia = new EstrategiaManoAlzada();
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
        estrategia = new EstrategiaCirculo();
        activarBotones();
        jButton5.setEnabled(false);
    }

    private void setEstrategiaBorrar(ActionEvent e) {
        datos.clear();
        canvas.repaint();
    }
}
