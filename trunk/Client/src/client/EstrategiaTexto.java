package client;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Font;
import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaTexto implements EstrategiaDibujo {
    TextRenderer renderer;
    // se pueden crear propiedades que no esten en las otras?
    String Texto;
    
    public EstrategiaTexto() {
        super();
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
        Texto = new String();
    }

    public boolean agregarPunto(Point punto) {
        return false;
    }

    public ArrayList<Point> obtenerDatos() {
        return null;
    }
    
    public void setTexto(String nuevoTexto) {
      Texto = nuevoTexto;
    }
}