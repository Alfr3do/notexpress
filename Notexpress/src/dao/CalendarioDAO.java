package dao;

import java.sql.Date;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Calendario;
import model.Dato;
import model.Lienzo;
import model.Usuario;

public class CalendarioDAO {
    public CalendarioDAO() {
        super();
    }

    public static void main(String[] args) {
        
        Calendario cDio = new Calendario();
        Timestamp fecha = new java.sql.Timestamp((Calendar.getInstance()).getTime().getTime());
        cDio.setFecha(fecha);
        

      Lienzo X = new Lienzo();
      X.setId_usuario(1);
      X.setNombre("nombreeee");
      
      
      Dato punto = new Dato();
      punto.setX(1);
      punto.setY(2);
      punto.setZ(0);
      punto.setTexto("HOLA SI WNO");
      
      Dato punto2 = new Dato();
      punto2.setX(10);
      punto2.setY(21);
      punto2.setZ(0);
        
        
      X.addPunto(punto);
      X.addPunto(punto2);
         
      
      
      LienzoDAO lDAO = new LienzoDAO();
      lDAO.save(X);
        
      cDio.setId_lienzo(X.getId());
      
      (new CalendarioDAO()).save(cDio);
            
    }


    public boolean save(Calendario calendario) {

        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(calendario);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean update(Calendario calendario) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(calendario);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean delete(Calendario calendario) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(calendario));
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;

    }


}
