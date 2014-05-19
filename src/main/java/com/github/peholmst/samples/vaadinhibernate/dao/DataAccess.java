package com.github.peholmst.samples.vaadinhibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DataAccess implements ServletContextListener {

    private static DataAccess INSTANCE;
    private EntityManagerFactory entityManagerFactory;

    static EntityManager createEntityManager() {
        return INSTANCE.entityManagerFactory.createEntityManager();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        entityManagerFactory = Persistence.createEntityManagerFactory("sample-pu");
        INSTANCE = this;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        entityManagerFactory.close();
        INSTANCE = null;
    }
}
