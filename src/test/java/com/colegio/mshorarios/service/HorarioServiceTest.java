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

        
        HorarioResponseDTO resultado = horarioService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(horarioRepository, times(1))
                .save(any(Horario.class));
    }

        @Test
        void obtenerHorarioPorId() {

        Horario horario = new Horario(
            1L,
            "LUNES",
            "08:00 - 09:30",
            "Sala 101",
            1L,
            1L,
            1L
        );

        when(horarioRepository.findById(1L))
            .thenReturn(Optional.of(horario));

        Optional<HorarioResponseDTO> resultado =
            horarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());

        verify(horarioRepository, times(1))
            .findById(1L);
        }

        @Test
        void eliminarHorario() {

        doNothing().when(horarioRepository)
            .deleteById(1L);

        horarioService.eliminar(1L);

        verify(horarioRepository, times(1))
            .deleteById(1L);
        }
}