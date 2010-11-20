package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaCirculo implements EstrategiaDibujo {
    
    Point centro, puntoPer;
    Boolean centroGuardado;
    
    public EstrategiaCirculo() {
        super();
        centroGuardado = false;
    }

    public boolean agregarPunto(Point punto) {
        
        if (centroGuardado) {
            puntoPer = punto;
            centroGuardado = false;
            return true;
        }
        else {
            centro = punto;
            centroGuardado = true;
        }
        return false;
    }

    public ArrayList<Point> obtenerDatos() {
        ArrayList<Point> tmp;
        
        double radio;
        radio = Math.sqrt(Math.pow(Math.abs(centro.x - puntoPer.x),2) + Math.pow(Math.abs(centro.y - puntoPer.y),2));
        
        tmp = new ArrayList<Point>();
        
        Point puntoTmp = new Point((int)(centro.x + radio), centro.y);
        
        tmp.add(puntoTmp);
        //tmp.add(new Point(puntoTmp.x, (int)(puntoTmp.y + radio)));
        tmp.add(new Point( (int)(centro.x + (radio)), (int)(centro.y + (radio/2))));
        tmp.add(new Point( (int)(centro.x + (radio/2)), (int)(centro.y + (radio))));
        //tmp.add(new Point((int)(centro.x - radio), (int)(centro.y + radio)));
        tmp.add(new Point(centro.x , (int)(centro.y + radio)));
        
        tmp.add(new Point(centro.x , (int)(centro.y + radio)));
        tmp.add(new Point((int)(centro.x - (radio/2)) , (int)(centro.y + radio)));
        tmp.add(new Point((int)(centro.x - radio), (int)(centro.y + (radio/2))));
        tmp.add(new Point((int)(centro.x - radio), (int)(centro.y)));
        
        tmp.add(new Point((int)(centro.x - radio), (int)(centro.y)));
        tmp.add(new Point((int)(centro.x - radio), (int)(centro.y - (radio/2))));
        tmp.add(new Point((int)(centro.x - (radio/2)), (int)(centro.y - radio)));
        tmp.add(new Point((int)(centro.x ), (int)(centro.y - radio)));
        
        tmp.add(new Point((int)(centro.x ), (int)(centro.y - radio)));
        tmp.add(new Point((int)(centro.x +(radio/2) ), (int)(centro.y - (radio))));
        tmp.add(new Point((int)(centro.x +(radio) ), (int)(puntoTmp.y-(radio/2))));
      tmp.add(new Point((int)(centro.x +(radio) ), (int)(puntoTmp.y)));
        //tmp.add(puntoTmp);
        return tmp;
    }
}
