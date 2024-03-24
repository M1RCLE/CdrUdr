package ru.mshindarev.CDR;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AccountDataAccess {
    private final EntityManager entityManager;

    public AccountDataAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findAccountById(long id) {
        return entityManager.find(Account.class, id);
    }

    public long dataAmount() {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("select count(*) from Account");
        Long count = (Long)query.getSingleResult();

        entityManager.getTransaction().commit();

        return count != null ? count : 0;
    }
    public void create(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    public void delete(Account account) {
        entityManager.getTransaction().begin();
        entityManager.remove(account);
        entityManager.getTransaction().commit();
    }
}
