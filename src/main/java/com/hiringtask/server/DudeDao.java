package com.hiringtask.server;

import com.hiringtask.server.model.Dude;
import org.hibernate.*;
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
        List<Dude> dudeList = null;
        try {
            tx = session.beginTransaction();
            dudeList = session.createCriteria(Dude.class)
                    .add(Restrictions.ge("id", start))
                    .add(Restrictions.lt("id", end)).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dudeList;
    }

    public List<Dude> getSortedListByRange(int start, int end, String column, boolean asc) {
        if (column == null) {
            return getListByRange(start, end);
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Dude> dudeList = null;
        String order = asc ? "asc" : "desc";
        String sql = "select * from dude order by " +
                column + " " +
                order + " limit " +
                String.valueOf(end - start) + " offset " +
                String.valueOf(start);
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(Dude.class);
            dudeList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return dudeList;
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