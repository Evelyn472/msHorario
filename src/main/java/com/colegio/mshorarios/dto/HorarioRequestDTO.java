package com.colegio.mshorarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioRequestDTO {

    @NotBlank(message = "El día no puede estar vacío")
    private String dia;

    @NotBlank(message = "El bloque de hora es obligatorio")
    private String bloqueHora;

    private String sala;

    @NotNull(message = "El cursoId es obligatorio")
    private Long cursoId;

    @NotNull(message = "El profesorId es obligatorio")
    private Long profesorId;

    @NotNull(message = "El asignaturaId es obligatorio")
    private Long asignaturaId;
}