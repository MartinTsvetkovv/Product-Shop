package com.tsvetkov.productshop.productshop.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    public Category() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
