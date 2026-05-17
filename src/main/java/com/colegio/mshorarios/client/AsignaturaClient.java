package com.colegio.mshorarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-asignaturas", url = "http://localhost:8085/api/asignatura")
public interface AsignaturaClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}