package gestion.dao;

import gestion.entities.Patient;
import gestion.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
public class PatientDAO {
    public void save(Patient patient){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(patient);
            tx.commit();
        }
    }

    public void update(Patient patient){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(patient);
            tx.commit();
        }
    }

    public void delete(String codePat){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            Patient patient = session.get(Patient.class, codePat);
            if(patient != null){
                session.remove(patient);
            }
            tx.commit();
        }
    }

    public Patient findByCode(String codePat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Patient.class, codePat);
        }
    }

    public List<Patient> findAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Patient ", Patient.class).list();
        }
    }
}
