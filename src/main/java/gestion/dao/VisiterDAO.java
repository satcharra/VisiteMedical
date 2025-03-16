package gestion.dao;

import gestion.entities.Medecin;
import gestion.entities.Visiter;
import gestion.entities.VisiterId;
import gestion.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Date;

public class VisiterDAO {
    public void save(Visiter visiter){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(visiter);
            tx.commit();
        }
    }

    public void update(Visiter visiter){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(visiter);
            tx.commit();
        }
    }

    public void delete(VisiterId id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            Visiter visiter = session.get(Visiter.class, id);
            if(visiter != null){
                session.remove(visiter);
            }
            tx.commit();
        }
    }
    public Visiter find(VisiterId id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Visiter.class, id);
        }
    }
    public List<Visiter> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Visiter ", Visiter.class).list();
        }
    }
}
