package client;

import java.awt.Point;

import java.util.ArrayList;

public interface EstrategiaDibujo {
    boolean agregarPunto(Point punto);
    void borrarDatos();
    ArrayList<Point> obtenerDatos();
}
