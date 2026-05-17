package com.colegio.mshorarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioResponseDTO {
    private Long id;
    private String dia;
    private String bloqueHora;
    private String sala;
    private Long cursoId;
    private Long profesorId;
    private Long asignaturaId;
}