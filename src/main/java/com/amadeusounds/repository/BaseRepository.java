package com.amadeusounds.repository;

import com.amadeusounds.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Vladica Jovanovaski on 4/9/2016.
 */
@Repository
public class BaseRepository {

    @Autowired
    private EntityManager entityManager;

    public <T extends BaseEntity> T findOne(Class<T> clazz, Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        if (specification != null) {
            criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public <T extends BaseEntity> List<T> findAll(Class clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public <T extends BaseEntity> List<T> findAll(Class clazz, Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        if (specification != null) {
            criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public <T extends BaseEntity> List<T> findAll(Class clazz, Specification<T> specification, Sort sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        if (specification != null) {
            criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public <T extends BaseEntity> Page<T> findAll(Class clazz, Specification<T> specification, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        if (specification != null) {
            criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }
        TypedQuery query = entityManager.createQuery(criteriaQuery);
        long total = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        Page<T> page = new PageImpl<T>(query.getResultList(), pageable, total);
        return page;
    }

    public <T extends BaseEntity> long count(Class clazz, Specification<T> specification) {
        return 0;
    }

    @Transactional
    public <T extends BaseEntity> T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public <T extends BaseEntity> void delete(T entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public <T extends BaseEntity> void delete(Class clazz, Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        T entity = entityManager.createQuery(criteriaQuery).getSingleResult();
        entityManager.remove(entity);
    }
}
