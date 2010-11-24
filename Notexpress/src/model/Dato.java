package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
  @NamedQuery(name = "Dato.findAll", query = "select o from Dato o")
})
public class Dato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ) 
    private Integer id;
    private Integer x;
    private Integer y;
    private Integer z;
    private String texto;
    @ManyToOne
    private Lienzo lienzo;
  
    public void setLienzo(Lienzo parent) {
      this.lienzo = parent;
   }


    public Dato() {
    }

    public Dato(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Integer getZ() {
        return z;
    }

    public Lienzo getLienzo() {
        return lienzo;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
