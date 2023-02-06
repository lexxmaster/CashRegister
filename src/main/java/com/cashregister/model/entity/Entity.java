package com.cashregister.model.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object){
        return id == ((Entity) object).getId();
    }

    @Override
    abstract public String toString();
}
