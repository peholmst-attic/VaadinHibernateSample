package com.github.peholmst.samples.vaadinhibernate.dao;

import com.github.peholmst.samples.vaadinhibernate.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PersonDao {

    private static final PersonDao INSTANCE = new PersonDao();

    private PersonDao() {
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    public List<Person> findAll() {
        final EntityManager em = DataAccess.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Person p ORDER BY p.lastName, p.firstName", Person.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Person save(Person p) {
        final EntityManager em = DataAccess.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (p.getId() == null) {
                em.persist(p);
            } else {
                p = em.merge(p);
            }
            em.flush();
            tx.commit();
            return p;
        } finally {
            em.close();
        }
    }

}
