package gestion.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Logger;
import java.util.logging.Level;

public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());
    private static SessionFactory sessionFactory;

    static {
        try {
            // 1. Création du registre de service
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            // 2. Création des métadonnées
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(gestion.entities.Medecin.class)
                    .addAnnotatedClass(gestion.entities.Patient.class)
                    .addAnnotatedClass(gestion.entities.Visiter.class)
                    .getMetadataBuilder()
                    .build();

            // 3. Création de la SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();

            logger.log(Level.INFO, "SessionFactory créée avec succès");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Échec de l'initialisation de SessionFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
