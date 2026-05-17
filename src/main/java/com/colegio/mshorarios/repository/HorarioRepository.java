package com.colegio.mshorarios.repository;

import com.colegio.mshorarios.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByCursoId(Long cursoId);

    @Query("SELECT h FROM Horario h WHERE LOWER(h.sala) LIKE LOWER(CONCAT('%', :sala, '%'))")
    List<Horario> buscarPorSala(@Param("sala") String sala);

    @Query("SELECT h FROM Horario h WHERE h.profesorId BETWEEN :min AND :max ORDER BY h.profesorId")
    List<Horario> findByProfesorIdBetween(@Param("min") Long min, @Param("max") Long max);
}