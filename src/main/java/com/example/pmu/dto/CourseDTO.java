package com.example.pmu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@ToString
public class CourseDTO {

    private Long id;

    @NotNull(message = "Le jour d'une course ne peut pas être null")
    @NotBlank(message = "Le jour d'une course ne peut pas être vide")
    private Date jour;

    @NotNull(message = "Le nom d'une course ne peut pas être null")
    @NotBlank(message = "Le nom d'une course ne peut pas être vide")
    private String nom;

    @NotNull(message = "Le numéro d'une course ne peut pas être null")
    @NotBlank(message = "Le numéro d'une course ne peut pas être vide")
    private String numero;

    @Size(min = 3)
    private List<PartantDTO> partantsDTO;
}
