package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.Query;

import model.Usuario;

public class UsuarioDAO {
    public UsuarioDAO() {
        super();
    }

    public static void main(String[] args) {

      /*  Usuario x = new Usuario();
        x.setDireccion("SSS");
        x.setNombre("Fernando");
        x.setEmail("fmunoasdfsdddzs@gmail.com");
        x.setTelefono("3017495295");
        x.setPassword("password");*/
        
        UsuarioDAO uDAO = (new UsuarioDAO());
        Usuario sho = uDAO.buscarPorEmail("fmunosdddzs@gmail.com");
        uDAO.delete(sho);
        /*uDAO.save(x);
        System.out.println(x.getId());
        uDAO.delete(x);
        */
        
    }


    public boolean save(Usuario usuario) {

        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(usuario);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean update(Usuario usuario) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(usuario);
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;


    }

    public boolean delete(Usuario usuario) {
        boolean flag = true;

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ArqLabPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(usuario));
            trans.commit();
        } catch (Exception e) {
            flag = false;
        }
        return flag;

    }


    public Usuario buscarPorEmail(String email) {
      boolean flag = true;

      EntityManagerFactory emf =
          Persistence.createEntityManagerFactory("ArqLabPU");
      EntityManager em = emf.createEntityManager();
      EntityTransaction trans = em.getTransaction();

      trans.begin();

      Query q = em.createQuery("Select U FROM Usuario U WHERE lower(U.email) = :email");
      q.setParameter("email", email.toLowerCase());

      trans.commit();

      return (Usuario) q.getSingleResult();
  
    }

}
