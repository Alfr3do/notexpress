package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaCuadrado implements EstrategiaDibujo {
    
    Point primero, segundo;
    Boolean primeroGuardado;
    public EstrategiaCuadrado() {
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
        tmp.add(new Point(Math.abs(segundo.x + primero.x)/2, Math.abs(primero.y + primero.y)/2));
        tmp.add(new Point(Math.abs(segundo.x + primero.x)/2, Math.abs(primero.y + primero.y)/2));
        tmp.add(new Point(segundo.x, primero.y));
        
        tmp.add(new Point(segundo.x, primero.y));
        tmp.add(new Point(Math.abs(segundo.x + segundo.x)/2, Math.abs(primero.y + segundo.y)/2));
        tmp.add(new Point(Math.abs(segundo.x + segundo.x)/2, Math.abs(primero.y + segundo.y)/2));
        tmp.add(segundo);
        
        tmp.add(segundo);
        tmp.add(new Point(Math.abs(primero.x + segundo.x)/2, Math.abs(segundo.y + segundo.y)/2));
        tmp.add(new Point(Math.abs(primero.x + segundo.x)/2, Math.abs(segundo.y + segundo.y)/2));    
        tmp.add(new Point(primero.x, segundo.y));
        
        tmp.add(new Point(primero.x, segundo.y));
        tmp.add(new Point(Math.abs(primero.x + primero.x)/2, Math.abs(primero.y + segundo.y)/2));
        tmp.add(new Point(Math.abs(primero.x + primero.x)/2, Math.abs(primero.y + segundo.y)/2));
        tmp.add(primero);
        return tmp;
    }
}
