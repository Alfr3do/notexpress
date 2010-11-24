package dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.Query;

import model.Dato;
import model.Lienzo;
import model.Usuario;

public class LienzoDAO {
    public LienzoDAO() {
        super();
    }

    public static void main(String[] args) {

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
    
   
            
    }


    public boolean save(Lienzo lienzo) {

        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(lienzo);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean update(Lienzo lienzo) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(lienzo);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean delete(Lienzo lienzo) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(lienzo));
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;

    }


  public Lienzo buscarPorId(int id) {
      boolean flag = true;

      EntityManagerFactory emf =
          Persistence.createEntityManagerFactory("ArqLabPU");
      EntityManager em = emf.createEntityManager();
      EntityTransaction trans = em.getTransaction();

      Lienzo lienzo = null;

      trans.begin();
      lienzo = em.find(Lienzo.class, id);
      trans.commit();

      return lienzo;

  }
  
  public Lienzo buscarPorNombre(String nombre) {
      boolean flag = true;

      EntityManagerFactory emf =
          Persistence.createEntityManagerFactory("ArqLabPU");
      EntityManager em = emf.createEntityManager();
      EntityTransaction trans = em.getTransaction();

      Lienzo lienzo = null;

      trans.begin();
    Query q = em.createQuery("Select U FROM Lienzo U WHERE lower(U.nombre) = :nombre");
    q.setParameter("nombre", nombre.toLowerCase());

    trans.commit();

    return (Lienzo) q.getSingleResult();

  }


  public Lienzo buscarPorOwner(int id_usuario) {
      boolean flag = true;

      EntityManagerFactory emf =
          Persistence.createEntityManagerFactory("ArqLabPU");
      EntityManager em = emf.createEntityManager();
      EntityTransaction trans = em.getTransaction();


    trans.begin();
    Query q = em.createQuery("Select U FROM Lienzo U WHERE U.id_usuario = :id_usuario");
    q.setParameter("id_usuario", id_usuario);
    q.setMaxResults(1);

    trans.commit();

      return (Lienzo)q.getSingleResult();

  }


}
