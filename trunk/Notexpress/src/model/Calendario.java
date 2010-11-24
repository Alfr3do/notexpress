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
    private int dia;
    private int mes;
    private int anio;
    
    public Calendario() {//creo que esto lo creo con el import? no, este es el modelo...bn
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


    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getDia() {
        return dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getMes() {
        return mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getAnio() {
        return anio;
    }
}
