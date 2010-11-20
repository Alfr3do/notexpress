package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaManoAlzada implements EstrategiaDibujo {
    
    private boolean puntoPrevio; 
    private Point puntoTmp;
    private static final int DIST_MIN = 10;
    ArrayList<Point> datos;
    
    public EstrategiaManoAlzada() {
        puntoPrevio = false;
        ArrayList<Point> datos = ArrayList<Point>();
        super();
    }

    public boolean agregarPunto(Point punto) {
        if (!puntoPrevio)
        {
          puntoPrevio = true;
          puntoTmp = punto;
        }
        else
        {
            double distancia =
               Math.sqrt(Math.pow(Math.abs(punto.x - puntoTmp.x),2) + Math.pow(Math.abs(punto.y - puntoTmp.y),2));
            if (distancia < DIST_MIN) {
              return false;
            }
            puntoPrevio = false;
            if (datos.size() ==0)
            {
              datos.add(puntoTmp);
              datos.add(punto);
              return false;
            }
            else {
              datos.add(puntoTmp);
              datos.add(punto);
              return true;
            }
        } 
        return false;
    }

    public ArrayList<Point> obtenerDatos() {
         ArrayList<Point>  tmp = datos;
        datos.clear();
        return tmp;
    }
}
