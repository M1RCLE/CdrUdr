package ru.mshindarev.CDR;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AccountDataAccess {
    private final EntityManager entityManager;

    public AccountDataAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findAccountById(long id) {
        return entityManager.find(Account.class, id);
    }

    public Account findAccountByPhoneNumber(String phoneNumber) {
        String hql = "select acc.id from Account acc where acc.accountPhoneNumber = :phoneNumber";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        query.setParameter("phoneNumber", phoneNumber);
        var result = query.getResultList();
        return result.isEmpty() ? null : findAccountById(result.get(0));
    }

    public long dataAmount() {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("select count(*) from Account");
        Long count = (Long) query.getSingleResult();

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
