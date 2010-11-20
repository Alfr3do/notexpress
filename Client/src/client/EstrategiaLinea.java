package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaLinea implements EstrategiaDibujo {
    
    Point primero, segundo;
    Boolean primeroGuardado;
    public EstrategiaLinea() {
        super();
        primeroGuardado = false;
    }

    public boolean agregarPunto(Point punto) {
        if (primeroGuardado) {
            segundo = punto;
            primeroGuardado = false;
            return true;
        }
        else {
            primero = punto;
            primeroGuardado = true;
        }
        return false;
        
    }

    public ArrayList<Point> obtenerDatos() {
        ArrayList<Point> tmp;
        tmp = new ArrayList<Point>();
        tmp.add(primero);
        tmp.add(new Point(Math.abs(segundo.x + primero.x)/2, Math.abs(segundo.y + primero.y)/2));
        tmp.add(new Point(Math.abs(segundo.x + primero.x)/2, Math.abs(segundo.y + primero.y)/2));
        tmp.add(segundo);
        return tmp;
    }
}
