package com.amadeusounds.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by Vladica Jovanovski on 3/15/2016.
 */

@MappedSuperclass
public class BaseEntity implements Comparable<BaseEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity be = (BaseEntity) obj;

        if (this.getId() == null || be.getId() == null) {
            return super.equals(obj);
        } else {
            return this.getId().equals(be.getId());
        }
    }

    @Override
    public int hashCode() {
        return this.id != null ? (this.getClass() + "-" + this.id).hashCode()
                : super.hashCode();
    }

    @Override
    public int compareTo(BaseEntity o) {
        return id.compareTo(o.getId());
    }
}

