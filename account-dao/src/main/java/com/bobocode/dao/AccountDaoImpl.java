package com.bobocode.dao;

import com.bobocode.exception.AccountDaoException;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AccountDaoImpl implements AccountDao {
    private EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Account account) {
        wrapWithinPersistenceContext(entityManager -> entityManager.persist(account));
    }

    @Override
    public Account findById(Long id) {
        return wrapWithResultWithinPersistenceContext(entityManager -> entityManager.find(Account.class, id));
    }

    @Override
    public Account findByEmail(String email) {
        return wrapWithResultWithinPersistenceContext(em ->
                em.createQuery("select a from Account a where a.email = :email", Account.class)
                        .setParameter("email", email)
                        .getSingleResult());
    }

    @Override
    public List<Account> findAll() {
        return wrapWithResultWithinPersistenceContext(em ->
                em.createQuery("select a from Account a", Account.class)
                        .getResultList());
    }

    @Override
    public void update(Account account) {
        wrapWithinPersistenceContext(em -> em.merge(account));
    }

    @Override
    public void remove(Account account) {
        wrapWithinPersistenceContext(em -> em.remove(em.merge(account)));
    }

    private void wrapWithinPersistenceContext(Consumer<EntityManager> action) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            action.accept(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new AccountDaoException("Error performing JPA operation. Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }

    private <T> T wrapWithResultWithinPersistenceContext(Function<EntityManager, T> action) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            T result = action.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new AccountDaoException("Error performing JPA operation. Transaction is rolled back", e);
        } finally {
            entityManager.close();
        }
    }
}

