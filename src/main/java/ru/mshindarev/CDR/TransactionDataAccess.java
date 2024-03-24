package ru.mshindarev.CDR;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class TransactionDataAccess {
    private final EntityManager entityManager;

    public TransactionDataAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Transaction account) {
//        entityManager.getTransaction().begin();
        entityManager.persist(account);
//        entityManager.getTransaction().commit();
    }

//    public void delete(Transaction account) {
//        entityManager.getTransaction().begin();
//        entityManager.remove(account);
//        entityManager.getTransaction().commit();
//    }
}
