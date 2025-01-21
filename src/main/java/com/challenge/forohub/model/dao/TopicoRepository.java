package com.challenge.forohub.model.dao;

import com.challenge.forohub.model.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository  extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
