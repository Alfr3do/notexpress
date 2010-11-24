package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries( { @NamedQuery(name = "Lienzo.findAll",
                             query = "select o from Lienzo o") })
public class Lienzo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer id_usuario;
    private String nombre;

    @OneToMany(mappedBy = "lienzo", cascade = CascadeType.PERSIST)
    private List<Dato> datos;

    public void clearDatos() {
        if (datos == null)
            datos = new ArrayList<Dato>();
        else
            datos.clear();
    }

    public void addPunto(Dato punto) {
        if (datos == null) {
            datos = new ArrayList<Dato>();
        }
        punto.setLienzo(this);
        datos.add(punto);

    }

    public Lienzo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDatos(List<Dato> datos) {
        this.datos = datos;

    }

    public List<Dato> getDatos() {
        return datos;
    }
}
