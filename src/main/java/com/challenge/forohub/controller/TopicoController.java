package com.challenge.forohub.controller;


import com.challenge.forohub.model.entity.Topico;
import com.challenge.forohub.model.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@Valid @RequestBody Topico topico) {
        if (topicoService.isTopicoDuplicado(topico.getTitulo(), topico.getMensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Topico nuevoTopico = topicoService.guardarTopico(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
    }

    @GetMapping
    public List<Topico> listarTopicos(@RequestParam Optional<String> curso, @RequestParam Optional<Integer> anio) {
        return topicoService.listarTopicos(curso, anio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalleTopico(@PathVariable Long id) {
        return topicoService.obtenerTopico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico topico) {
        return topicoService.actualizarTopico(id, topico)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        if (topicoService.eliminarTopico(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
