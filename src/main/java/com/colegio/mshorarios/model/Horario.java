package com.colegio.mshorarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String dia;

    @Column(name = "bloque_hora", nullable = false, length = 20)
    private String bloqueHora;

    @Column(length = 50)
    private String sala;

    @Column(name = "curso_id", nullable = false)
    private Long cursoId;

    @Column(name = "profesor_id", nullable = false)
    private Long profesorId;

    @Column(name = "asignatura_id", nullable = false)
    private Long asignaturaId;
}