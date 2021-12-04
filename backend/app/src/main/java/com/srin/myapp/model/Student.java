package com.srin.myapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn
    private Major major;
}