package gestion.dao;

import gestion.entities.Medecin;
import gestion.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
public class MedecinDAO {
    public void save(Medecin medecin){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(medecin);
            tx.commit();
        }
    }

    public void update(Medecin medecin){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(medecin);
            tx.commit();
        }
    }

    public void delete(String codeMed){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            Medecin medecin = session.get(Medecin.class, codeMed);
            if(medecin != null){
                session.remove(medecin);
            }
            tx.commit();
        }
    }

    public Medecin findByCode(String codeMed) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Medecin.class, codeMed);
        }
    }

    public List<Medecin> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Medecin ", Medecin.class).list();
        }
    }
}
