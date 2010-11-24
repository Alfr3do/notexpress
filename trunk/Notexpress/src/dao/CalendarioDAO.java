package dao;

import java.sql.Date;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import javax.persistence.Query;

import model.Calendario;
import model.Dato;
import model.Lienzo;
import model.Usuario;

public class CalendarioDAO {
    public CalendarioDAO() {
        super();
    }

    public static void main(String[] args) {
        
    
            
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

  public Calendario buscarPorFecha(int dia, int mes, int anio) {
    boolean flag = true;

    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("ArqLabPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction trans = em.getTransaction();

    trans.begin();

    Query q = em.createQuery("Select U FROM Calendario U WHERE U.dia = :dia and U.mes = :mes and U.anio = :anio");
    q.setParameter("dia", dia);
    q.setParameter("anio", anio);
    q.setParameter("mes", mes);
    

    trans.commit();

    try {
        Calendario cale = (Calendario) q.getSingleResult();
        return cale;
    }
    catch (NoResultException e) {
        return null;
        }
    
  
  }

}
