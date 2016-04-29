package com.amadeusounds.util;

import com.amadeusounds.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

/**
 * Created by Vladica Jovanovski on 4/6/2016.
 */
@Service
public class SpecificationUtils {

    public static <T> Specification<T> equals(String path, Object param) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(getPath(root, path), param);
            }
        };
    }

    public static <T> Specification<T> lessThan(String path, Comparable param, boolean inclusive) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (inclusive) {
                  return criteriaBuilder.lessThanOrEqualTo(getPath(root, path), param);
                }
                return criteriaBuilder.lessThan(getPath(root, path), param);
            }
        };
    }

    public static <T> Specification<T> greaterThan(String path, Comparable param, boolean inclusive) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (inclusive) {
                    return criteriaBuilder.greaterThanOrEqualTo(getPath(root, path), param);
                }
                return criteriaBuilder.greaterThan(getPath(root, path), param);
            }
        };
    }

    public static Path getPath(Root root, String expr) {
        String[] split = expr.split("\\.");
        Path path = root;
        for (String p : split) {
            path = path.get(p);
        }
        return path;
    }
}
