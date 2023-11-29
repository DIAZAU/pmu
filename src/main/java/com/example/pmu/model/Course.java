package com.example.pmu.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Course implements Serializable {
    @Id
    private Long id;

    private Date jour;

    private String nom;

    private String numero;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Partant> partants;
}
