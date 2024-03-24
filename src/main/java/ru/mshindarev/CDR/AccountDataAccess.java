package ru.mshindarev.CDR;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class AccountDataAccess {
    private final EntityManager entityManager;

    public AccountDataAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account findAccountById(long id) {
        return entityManager.find(Account.class, id);
    }

//    public Account getAccountByPhoneNumber(String phoneNumber) {
//        entityManager.getTransaction().begin();
//        String hql = "select acc from Account acc where Account.accountPhoneNumber = :phoneNumber";
//        TypedQuery<Account> query = entityManager.createQuery(hql, Account.class);
//        return query.setParameter("phoneNumber", phoneNumber).getFirstResult();
//    }

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
