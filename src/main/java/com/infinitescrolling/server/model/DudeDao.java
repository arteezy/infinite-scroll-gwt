package com.infinitescrolling.server.model;

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

    public void saveDude(Dude dude) {
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
            List dudes = session.createQuery("FROM DUDE").list();
            for (Iterator iterator = dudes.iterator(); iterator.hasNext(); ) {
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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
        }
        return dudeList;
    }

    public List<Dude> getSortedListByRange(int start, int end, String column, boolean asc) {
        if (column == null) {
            return getListByRange(start, end);
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Dude> dudeList = null;
        String order = asc ? "ASC" : "DESC";
        String sql = "SELECT * FROM DUDE ORDER BY " +
                column + " " +
                order + " LIMIT " +
                String.valueOf(end - start) + " OFFSET " +
                String.valueOf(start);
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(Dude.class);
            dudeList = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
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

    public void clearTable() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE DUDE IF EXISTS").executeUpdate();
            //session.createSQLQuery("CHECKPOINT DEFRAG").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Intentionally bypassing Hibernate schema creation on update
    public void createSchema() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE MEMORY TABLE PUBLIC.DUDE(ID INTEGER NOT NULL PRIMARY KEY,FNAME VARCHAR(255),LNAME VARCHAR(255))").executeUpdate();
            session.createSQLQuery("CREATE INDEX FNAMEINDEX ON PUBLIC.DUDE(FNAME)").executeUpdate();
            session.createSQLQuery("CREATE INDEX LNAMEINDEX ON PUBLIC.DUDE(LNAME)").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void prepare() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("SET FILES LOG FALSE").executeUpdate();
            session.createSQLQuery("SET DATABASE GC 10000").executeUpdate();
            session.createSQLQuery("SET FILES NIO SIZE 512").executeUpdate();
            session.createSQLQuery("SET DATABASE TRANSACTION CONTROL MVCC").executeUpdate();
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