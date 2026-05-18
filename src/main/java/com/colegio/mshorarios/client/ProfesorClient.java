package com.colegio.mshorarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-profesores", url = "http://localhost:8083/api/v1/profesor")
public interface ProfesorClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}