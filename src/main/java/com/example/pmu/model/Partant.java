package com.example.pmu.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Partant implements Serializable {
    @Id
    private Long id;

    private String nom;

    private String numero;

    @ManyToOne(fetch= FetchType.LAZY)
    private Course course;
}
