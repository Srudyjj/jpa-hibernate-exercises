package com.bobocode.dao;

import com.bobocode.model.Photo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Please note that you should not use auto-commit mode for your implementation.
 */
public class PhotoDaoImpl implements PhotoDao {
    private EntityManagerFactory entityManagerFactory;

    public PhotoDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Photo photo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(photo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public Photo findById(long id) {
        throw new UnsupportedOperationException("Just do it!"); // todo
    }

    @Override
    public List<Photo> findAll() {
        throw new UnsupportedOperationException("Just do it!"); // todo
    }

    @Override
    public void remove(Photo photo) {
        throw new UnsupportedOperationException("Just do it!"); // todo
    }

    @Override
    public void addComment(long photoId, String comment) {
        throw new UnsupportedOperationException("Just do it!"); // todo
    }
}
