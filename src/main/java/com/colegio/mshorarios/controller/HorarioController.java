package com.colegio.mshorarios.controller;

import com.colegio.mshorarios.dto.HorarioRequestDTO;
import com.colegio.mshorarios.dto.HorarioResponseDTO;
import com.colegio.mshorarios.service.HorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/horario")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @GetMapping
    public List<HorarioResponseDTO> obtenerTodas() {
        return horarioService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return horarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{cursoId}")
    public List<HorarioResponseDTO> porCurso(@PathVariable Long cursoId) {
        return horarioService.obtenerPorCurso(cursoId);
    }

    @GetMapping("/buscar")
    public List<HorarioResponseDTO> buscarPorSala(@RequestParam String sala) {
        return horarioService.buscarPorSala(sala);
    }

    @GetMapping("/profesor")
    public List<HorarioResponseDTO> porProfesor(@RequestParam Long min, @RequestParam Long max) {
        return horarioService.buscarPorRangoProfesor(min, max);
    }

    @PostMapping
    public ResponseEntity<HorarioResponseDTO> crear(@Valid @RequestBody HorarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(horarioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HorarioRequestDTO dto) {
        return horarioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}