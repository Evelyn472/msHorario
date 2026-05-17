package com.colegio.mshorarios.service;

import com.colegio.mshorarios.client.CursoClient;
import com.colegio.mshorarios.client.ProfesorClient;
import com.colegio.mshorarios.client.AsignaturaClient;
import com.colegio.mshorarios.dto.HorarioRequestDTO;
import com.colegio.mshorarios.dto.HorarioResponseDTO;
import com.colegio.mshorarios.model.Horario;
import com.colegio.mshorarios.repository.HorarioRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;

    private final CursoClient cursoClient;
    private final ProfesorClient profesorClient;
    private final AsignaturaClient asignaturaClient;

    private HorarioResponseDTO mapToDTO(Horario h) {
        return new HorarioResponseDTO(
                h.getId(), h.getDia(), h.getBloqueHora(),
                h.getSala(), h.getCursoId(), h.getProfesorId(), h.getAsignaturaId());
    }

    private void validarComponentesHorario(Long cursoId, Long profesorId, Long asignaturaId) {
      
        try {
            cursoClient.obtenerPorId(cursoId);
            log.info(">>> Curso {} validado correctamente (FeignClient)", cursoId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("El curso con id " + cursoId + " no existe en ms-cursos.");
        } catch (FeignException e) {
            throw new RuntimeException("No se puede conectar con ms-cursos: " + e.getMessage());
        }

        
        try {
            profesorClient.obtenerPorId(profesorId);
            log.info(">>> Profesor {} validado correctamente (FeignClient)", profesorId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("El profesor con id " + profesorId + " no existe en ms-profesores.");
        } catch (FeignException e) {
            throw new RuntimeException("No se puede conectar con ms-profesores: " + e.getMessage());
        }

       
        try {
            asignaturaClient.obtenerPorId(asignaturaId);
            log.info(">>> Asignatura {} validada correctamente (FeignClient)", asignaturaId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("La asignatura con id " + asignaturaId + " no existe en ms-asignaturas.");
        } catch (FeignException e) {
            throw new RuntimeException("No se puede conectar con ms-asignaturas: " + e.getMessage());
        }
    }

    
    public List<HorarioResponseDTO> obtenerTodas() {
        return horarioRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<HorarioResponseDTO> obtenerPorId(Long id) {
        return horarioRepository.findById(id).map(this::mapToDTO);
    }

    public List<HorarioResponseDTO> obtenerPorCurso(Long cursoId) {
        return horarioRepository.findByCursoId(cursoId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<HorarioResponseDTO> buscarPorSala(String sala) {
        return horarioRepository.buscarPorSala(sala)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<HorarioResponseDTO> buscarPorRangoProfesor(Long min, Long max) {
        return horarioRepository.findByProfesorIdBetween(min, max)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public HorarioResponseDTO guardar(HorarioRequestDTO dto) {
        validarComponentesHorario(dto.getCursoId(), dto.getProfesorId(), dto.getAsignaturaId());
        Horario h = new Horario(null, dto.getDia(), dto.getBloqueHora(),
                dto.getSala(), dto.getCursoId(), dto.getProfesorId(), dto.getAsignaturaId());
        return mapToDTO(horarioRepository.save(h));
    }

    public Optional<HorarioResponseDTO> actualizar(Long id, HorarioRequestDTO dto) {
        return horarioRepository.findById(id).map(existente -> {
            validarComponentesHorario(dto.getCursoId(), dto.getProfesorId(), dto.getAsignaturaId());
            existente.setDia(dto.getDia());
            existente.setBloqueHora(dto.getBloqueHora());
            existente.setSala(dto.getSala());
            existente.setCursoId(dto.getCursoId());
            existente.setProfesorId(dto.getProfesorId());
            existente.setAsignaturaId(dto.getAsignaturaId());
            return mapToDTO(horarioRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        horarioRepository.deleteById(id);
    }
}