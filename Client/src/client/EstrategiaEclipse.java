package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaEclipse implements EstrategiaDibujo {
    
    Point centro, radio1;
    Boolean CRGuardados;
    public EstrategiaEclipse() {
        super();
        CRGuardados = false;
    }

    public boolean agregarPunto(Point punto) {
        return false;
    }

    public ArrayList<Point> obtenerDatos() {
        return null;
    }
}
