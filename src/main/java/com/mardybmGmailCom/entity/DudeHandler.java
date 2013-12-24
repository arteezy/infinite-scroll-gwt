package com.mardybmGmailCom.entity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class DudeHandler {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;

    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        DudeHandler handle = new DudeHandler();

        Integer dude1 = handle.addDude("Johnny", "Doe");
        Integer dude2 = handle.addDude("Mike", "Ghannam");

        handle.updateDude(dude1);
        handle.deleteDude(dude2);
        handle.listDudes();

        //factory.close();
    }

    public Integer addDude(String fname, String lname) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer dudeID = null;
        try {
            tx = session.beginTransaction();
            Dude dude = new Dude(fname, lname);
            dudeID = (Integer) session.save(dude);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dudeID;
    }

    public void listDudes() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Dude").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                Dude dude = (Dude) iterator.next();
                System.out.print("First Name: " + dude.getFirstName());
                System.out.println(" Last Name: " + dude.getLastName());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateDude(Integer DudeID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Dude dude = (Dude) session.get(Dude.class, DudeID);
            session.update(dude);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteDude(Integer DudeID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Dude dude = (Dude) session.get(Dude.class, DudeID);
            session.delete(dude);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}