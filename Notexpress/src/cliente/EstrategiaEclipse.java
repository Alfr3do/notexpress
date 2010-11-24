package client;

import java.awt.Point;

import java.util.ArrayList;

public class EstrategiaEclipse implements EstrategiaDibujo {
    
    Point centro;
    int radio1;
    int CRGuardados;
    ArrayList<Point> datos;
    public EstrategiaEclipse() {
        super();
        datos = new ArrayList<Point>();    
        CRGuardados = 0;
    }

    public boolean agregarPunto(Point punto) {
        if (CRGuardados==0) {
            centro = punto;
            CRGuardados++;
        return false;  
        }
        if (CRGuardados==1){
            double radio;
            radio =
                    Math.sqrt(Math.pow(Math.abs(centro.x - punto.x), 2) + Math.pow(Math.abs(centro.y -
                                                                                               punto.y),
                                                                                      2));
            radio1 = (int)radio;
            CRGuardados++;
           return false; 
          }
      double radio;
      radio =
              Math.sqrt(Math.pow(Math.abs(centro.x - punto.x), 2) + Math.pow(Math.abs(centro.y -
                                                                                         punto.y),
                                                                        2));
      /*if (radio > radio1)
        {
          int tmpR = radio1;    
          radio1 = (int)radio;
          radio = radio1;
        }*/
      Point puntoTmp = new Point((int)(centro.x + radio1), centro.y);

      datos.add(puntoTmp);
      //tmp.add(new Point(puntoTmp.x, (int)(puntoTmp.y + radio)));
      datos.add(new Point((int)(centro.x + (radio1)),
                        (int)(centro.y + (radio / 2))));
      datos.add(new Point((int)(centro.x + (radio1 / 2)),
                        (int)(centro.y + (radio))));
      //datos.add(new Point((int)(centro.x - radio), (int)(centro.y + radio)));
      datos.add(new Point(centro.x, (int)(centro.y + radio)));

      datos.add(new Point(centro.x, (int)(centro.y + radio)));
      datos.add(new Point((int)(centro.x - (radio1 / 2)),
                        (int)(centro.y + radio)));
      datos.add(new Point((int)(centro.x - radio1),
                        (int)(centro.y + (radio / 2))));
      datos.add(new Point((int)(centro.x - radio1), (int)(centro.y)));

      datos.add(new Point((int)(centro.x - radio1), (int)(centro.y)));
      datos.add(new Point((int)(centro.x - radio1),
                        (int)(centro.y - (radio / 2))));
      datos.add(new Point((int)(centro.x - (radio1 / 2)),
                        (int)(centro.y - radio)));
      datos.add(new Point((int)(centro.x), (int)(centro.y - radio)));

      datos.add(new Point((int)(centro.x), (int)(centro.y - radio)));
      datos.add(new Point((int)(centro.x + (radio1 / 2)),
                        (int)(centro.y - (radio))));
      datos.add(new Point((int)(centro.x + (radio1)),
                        (int)(puntoTmp.y - (radio / 2))));
      datos.add(new Point((int)(centro.x + (radio1)), (int)(puntoTmp.y))); 
      return true;
    }

    public ArrayList<Point> obtenerDatos() {
      return datos;
    }

    public void borrarDatos() {
        datos.clear();
        CRGuardados = 0;
    }
}
