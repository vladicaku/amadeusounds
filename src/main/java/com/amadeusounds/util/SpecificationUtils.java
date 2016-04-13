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

    public static Path getPath(Root root, String expr) {
        String[] split = expr.split("\\.");
        Path path = root;
        for (String p : split) {
            path = path.get(p);
        }
        return path;
    }
}
