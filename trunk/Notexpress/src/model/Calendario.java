package model;

import java.io.Serializable;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
  @NamedQuery(name = "Calendario.findAll", query = "select o from Calendario o")
})
public class Calendario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ) 
    private Integer id;

    private Integer id_lienzo;
    private Timestamp fecha;
    
    public Calendario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId_lienzo(Integer id_lienzo) {
        this.id_lienzo = id_lienzo;
    }

    public Integer getId_lienzo() {
        return id_lienzo;
    }


    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFecha() {
        return fecha;
    }
}
