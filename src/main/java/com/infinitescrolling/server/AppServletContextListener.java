package com.infinitescrolling.server;

import com.infinitescrolling.server.model.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");

        String rootPath = arg0.getServletContext().getRealPath("/");
        System.setProperty("webroot", rootPath);

        HibernateUtil.getSessionFactory();
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");

        if (!HibernateUtil.getSessionFactory().isClosed()) {
            HibernateUtil.getSessionFactory().openSession().createSQLQuery("SHUTDOWN").executeUpdate();
            HibernateUtil.getSessionFactory().close();
            System.out.println("DB is successfully closed");
        }
    }
}