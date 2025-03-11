package gestion;

import gestion.entities.*;
import gestion.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println("Démarrage de l'application...");

            // Initialisation d'Hibernate (créera les tables)
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            // Opération de test (facultatif)
            System.out.println("Création des tables réussie !");

            tx.commit();
            session.close();

            System.out.println("Base de données prête !");
            System.out.println("Vous pouvez maintenant vérifier les tables dans PostgreSQL");

            HibernateUtil.shutdown();
        } catch (Exception e) {
            System.err.println("Erreur d'initialisation :");
            e.printStackTrace();
        }
    }
}
