package com.generation.pixelstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.pixelstore.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

   public List<Jogo> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}