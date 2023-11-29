package com.example.pmu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PartantDTO {
    private Long id;

    @NotNull(message = "Le jour d'un partant ne peut pas être null")
    @NotBlank(message = "Le jour d'un partant ne peut pas être vide")
    private String nom;

    @NotNull(message = "Le numéro d'un partant ne peut pas être null")
    @NotBlank(message = "Le numéro d'un partant ne peut pas être vide")
    private String numero;
}
