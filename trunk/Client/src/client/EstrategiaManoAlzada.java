package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaManoAlzada implements EstrategiaDibujo {
    
    private boolean puntoPrevio; 
    private Point puntoTmp;
    private static final int DIST_MIN = 10;
    ArrayList<Point> datos;
    
    private Debug debugInstance;

  public EstrategiaManoAlzada() {
      super();
      puntoPrevio = false;
      ArrayList<Point> datos = new ArrayList<Point>();
      debugInstance = new Debug();
      
      debugInstance.setDebug(false);
  }

    /**
     * @param parDebug
     */
    public EstrategiaManoAlzada(boolean parDebug) {
        super();
        puntoPrevio = false;
        ArrayList<Point> datos = new ArrayList<Point>();
        debugInstance = new Debug();
        
        debugInstance.setDebug(parDebug);
    }

    public boolean agregarPunto(Point punto) {
        if (!puntoPrevio)
        {
          puntoPrevio = true;
          puntoTmp = punto;
        }
        else
        {
            puntoTmp = punto;
            double distancia =
               Math.sqrt(Math.pow(Math.abs(punto.x - puntoTmp.x),2) + Math.pow(Math.abs(punto.y - puntoTmp.y),2));
            if (distancia < DIST_MIN) {
              return false;
            }
            puntoPrevio = false;
            if (datos != null && datos.size() ==0)
            {
              try
                  {
              datos.add(puntoTmp);
              datos.add(punto);
                  }
              catch (Exception e) {
                debugInstance.imprimir("Error agregando punto.", e);
              }
              return false;
            }
            else {
                try
              {
              datos.add(puntoTmp);
              datos.add(punto);
              }
              catch (Exception e) {
                debugInstance.imprimir("Error agregando punto.", e);
              }
              return true;
            }
        } 
        return false;
    }

    public ArrayList<Point> obtenerDatos() {
      ArrayList<Point>  tmp = datos;
        try{ 
        datos.clear();
        }
        catch (Exception e) {
            return null;
        }
        return tmp;
    }
}
