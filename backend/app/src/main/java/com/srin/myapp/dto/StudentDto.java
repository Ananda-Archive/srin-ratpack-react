package com.srin.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class StudentDto {
    private Long id;
    private String name;
    private String email;
    private int semester;
    private Long majorId;
}