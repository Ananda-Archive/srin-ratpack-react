package com.srin.myapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Major {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Major(){};
    public Major(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
