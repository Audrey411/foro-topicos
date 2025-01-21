package com.challenge.forohub.model.service;


import com.challenge.forohub.model.dao.TopicoRepository;
import com.challenge.forohub.model.entity.Topico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;


    public boolean isTopicoDuplicado(String titulo, String mensaje) {
        return topicoRepository.existsByTituloAndMensaje(titulo, mensaje);
    }

    public Topico guardarTopico(Topico topico) {
        return topicoRepository.save(topico);
    }

    public List<Topico> listarTopicos(Optional<String> curso, Optional<Integer> anio) {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .filter(t -> curso.map(c -> c.equals(t.getCurso())).orElse(true))
                .filter(t -> anio.map(y -> y == t.getFechaCreacion().getYear()).orElse(true))
                .limit(10)
                .sorted(Comparator.comparing(Topico::getFechaCreacion))
                .collect(Collectors.toList());
    }

    public Optional<Topico> obtenerTopico(Long id) {
        return topicoRepository.findById(id);
    }

    public Optional<Topico> actualizarTopico(Long id, Topico topico) {
        return topicoRepository.findById(id).map(t -> {
            t.setTitulo(topico.getTitulo());
            t.setMensaje(topico.getMensaje());
            t.setStatus(topico.getStatus());
            t.setAutor(topico.getAutor());
            t.setCurso(topico.getCurso());
            return topicoRepository.save(t);
        });
    }

    public boolean eliminarTopico(Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
