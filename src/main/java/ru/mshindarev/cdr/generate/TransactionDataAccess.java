package ru.mshindarev.cdr.generate;

import jakarta.persistence.EntityManager;
import ru.mshindarev.cdr.model.Transaction;

public class TransactionDataAccess {
    private final EntityManager entityManager;

    public TransactionDataAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Transaction account) {
        entityManager.persist(account);
    }
}
