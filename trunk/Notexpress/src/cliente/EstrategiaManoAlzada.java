package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaManoAlzada implements EstrategiaDibujo {
    
    private boolean puntoPrevio; 
    private Point puntoTmp;
    private static final int DIST_MIN = 10;
    ArrayList<Point> datos;
    private boolean continuo;
    boolean parEnviados;
    private Debug debugInstance;

  public EstrategiaManoAlzada() {
      super();
      puntoPrevio = false;
      ArrayList<Point> datos = new ArrayList<Point>();
      debugInstance = new Debug();
      puntoTmp = new Point(0,0); 
      debugInstance.setDebug(false);
      parEnviados = false;
  }

    /**
     * @param parDebug
     */
    public EstrategiaManoAlzada(boolean parDebug) {
        super();
        puntoPrevio = false;
        ArrayList<Point> datos = new ArrayList<Point>();
        debugInstance = new Debug();
        parEnviados = false;
        debugInstance.setDebug(parDebug);
    }

    public boolean agregarPunto(Point punto) {
      Point tmpP;
        if (datos == null)
        {
            debugInstance.imprimir("recreando los datos");
            datos = new ArrayList<Point>();
        }
        else if (parEnviados)
        {
           parEnviados = false;
           if (continuo) {
              int dir = 0;
              //derminar la direccion
              if (datos.size() >=4) {
                if (datos.get(3).x - datos.get(0).x >0) {
                  dir = dir | 2;
                }
                else {
                  dir = dir | 1;
                }
                if (datos.get(3).y - datos.get(0).y >0) {
                  dir = dir | 8;
                }
                else {
                  dir = dir | 4;
                }
              }
             double distanciaX =Math.abs(datos.get(3).x - datos.get(0).x);
             double distanciaY = Math.abs(datos.get(3).y - datos.get(0).y);
              datos.remove(0);
              datos.remove(0);
              tmpP = datos.get(0);
              datos.remove(0);
              //agregar segun la dir
              if (dir == 0)
              {
                datos.add(tmpP);
              }
              else {
                  Point finalP = tmpP;
                  if (dir == 1){
                    finalP = new Point(tmpP.x -(int)distanciaX, tmpP.y);
                  }
                  if (dir == 2){
                    finalP = new Point(tmpP.x +(int)distanciaX, tmpP.y);
                  }
                  if (dir == 4){
                    finalP = new Point(tmpP.x , tmpP.y-(int)distanciaY);
                  }
                  if (dir == 5){
                    finalP = new Point(tmpP.x -(int)distanciaX, tmpP.y-(int)distanciaY);
                  }
                  if (dir == 6){
                    finalP = new Point(tmpP.x +(int)distanciaX, tmpP.y-(int)distanciaY);
                  }
                  if (dir == 8){
                    finalP = new Point(tmpP.x , tmpP.y+(int)distanciaY);
                  }
                  if (dir == 9){
                    finalP = new Point(tmpP.x -(int)distanciaX, tmpP.y+(int)distanciaY);
                  }
                  if (dir == 10){
                    finalP = new Point(tmpP.x +(int)distanciaX, tmpP.y+(int)distanciaY);
                  }
                datos.add(finalP);
              }
           }
            else {
             debugInstance.imprimir("fin de la continuidad aquis");
             datos.clear();
               parEnviados = false;
             puntoPrevio = false;
           }
        }
        else if (!continuo)
          {
              debugInstance.imprimir("fin de la continuidad");
              borrarDatos();
              datos.clear();
              parEnviados = false;
              puntoPrevio = false;
            }
            
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
              debugInstance.imprimir("Listo ya hay cuatro "+datos);
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

   public void borrarDatos() {
    if (datos != null)       
     datos.clear();
   }
  
    public ArrayList<Point> obtenerDatos() {
        parEnviados = true;
        return datos;
    }

    public void setContinuo(boolean continuo) {
        this.continuo = continuo;
    }

    public boolean isContinuo() {
        return continuo;
    }

}
