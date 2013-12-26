package com.mardybmGmailCom.server;

import com.mardybmGmailCom.server.model.Dude;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Iterator;
import java.util.List;

public class DudeDao {
    public Integer addDude(String fname, String lname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer dudeID = null;
        try {
            tx = session.beginTransaction();
            Dude dude = new Dude();
            dude.setFirstName(fname);
            dude.setLastName(lname);
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

    public void save(Dude dude) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(dude);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void saveList(List<Dude> dudeList) {
        StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Dude d : dudeList) session.insert(d);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void listDudes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    public Dude findById(int DudeID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Dude dude = null;
        try {
            tx = session.beginTransaction();
            dude = (Dude) session.get(Dude.class, DudeID);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dude;
    }

    public List<Dude> getListByRange(int start, int end) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Dude> dude = null;
        try {
            tx = session.beginTransaction();
            dude = session.createCriteria(Dude.class)
                    .add(Restrictions.ge("id", start))
                    .add(Restrictions.lt("id", end)).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dude;
    }

    public void updateDude(Integer DudeID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
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