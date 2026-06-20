package com.colegio.mshorarios.service;

import com.colegio.mshorarios.client.AsignaturaClient;
import com.colegio.mshorarios.client.CursoClient;
import com.colegio.mshorarios.client.ProfesorClient;
import com.colegio.mshorarios.dto.HorarioRequestDTO;
import com.colegio.mshorarios.dto.HorarioResponseDTO;
import com.colegio.mshorarios.model.Horario;
import com.colegio.mshorarios.repository.HorarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioServiceTest {

    @Mock
    private HorarioRepository horarioRepository;

    @Mock
    private CursoClient cursoClient;

    @Mock
    private ProfesorClient profesorClient;

    @Mock
    private AsignaturaClient asignaturaClient;

    @InjectMocks
    private HorarioService horarioService;

    @Test
    void guardarHorarioCorrectamente() {

        // GIVEN
        HorarioRequestDTO dto = new HorarioRequestDTO(
                "LUNES",
                "08:00-09:30",
                "Sala 1",
                1L,
                1L,
                1L
        );

        Horario horarioGuardado = new Horario(
                1L,
                "LUNES",
                "08:00-09:30",
                "Sala 1",
                1L,
                1L,
                1L
        );

        when(cursoClient.obtenerPorId(1L)).thenReturn(new Object());
        when(profesorClient.obtenerPorId(1L)).thenReturn(new Object());
        when(asignaturaClient.obtenerPorId(1L)).thenReturn(new Object());

        when(horarioRepository.save(any(Horario.class)))
                .thenReturn(horarioGuardado);

        // WHEN
        HorarioResponseDTO resultado = horarioService.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(horarioRepository, times(1))
                .save(any(Horario.class));
    }
}