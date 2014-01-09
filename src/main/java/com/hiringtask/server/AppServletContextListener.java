package com.hiringtask.server;

import com.hiringtask.server.model.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        System.out.println("<------ Oh My Fucking God ------>");
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
        System.out.println("<--------- Holy Shit ------->");

        if (!HibernateUtil.getSessionFactory().isClosed()) {
            HibernateUtil.getSessionFactory().openSession().createSQLQuery("SHUTDOWN").executeUpdate();
            System.out.println("<--------- SUCCESSFUL SHUTDOWN ------->");
            HibernateUtil.getSessionFactory().close();
            System.out.println("<--------- CLOSED ------->");
        }
    }
}