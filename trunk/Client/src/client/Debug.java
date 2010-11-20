package client;

import javax.swing.JFrame;

public class Debug extends JFrame {
    
    Boolean debug;
    public Debug() {
        super();
    }
    public Debug(String title) {
        super(title);
    }
    
    public void imprimir(String mensaje) {
        //TODO: Encontrar la traza 
        if (debug) {
            System.out.println("[Debug]: "+mensaje);
        }
    }
    public void imprimir(double mensaje) {
        //TODO: Encontrar la traza 
        if (debug) {
            System.out.println("[Debug]: "+mensaje);
        }
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Boolean getDebug() {
        return debug;
    }
}
