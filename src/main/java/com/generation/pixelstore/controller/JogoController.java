package com.generation.pixelstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.pixelstore.model.Jogo;
import com.generation.pixelstore.repository.CategoriaRepository;
import com.generation.pixelstore.repository.JogoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JogoController {
    
    @Autowired
    private JogoRepository jogoRepository;

    
    @GetMapping
    public ResponseEntity<List<Jogo>> getAll(){
        return ResponseEntity.ok(jogoRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Jogo> getById(@PathVariable Long id){
        return jogoRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Jogo>> getByTitle(@PathVariable 
    String descricao){
        return ResponseEntity.ok(jogoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }
    
    @PostMapping
    public ResponseEntity<Jogo> post(@Valid @RequestBody Jogo jogo){
    	if (jogoRepository.existsById(jogo.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(jogoRepository.save(jogo));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
    }
    
    @PutMapping
    public ResponseEntity<Jogo> put(@Valid @RequestBody Jogo jogo){
    	if (jogoRepository.existsById(jogo.getId())) {

			if (jogoRepository.existsById(jogo.getCategoria().getId()))

				return ResponseEntity.status(HttpStatus.OK).body(jogoRepository.save(jogo));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogo não encontrado!", null);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Jogo> jogo = jogoRepository.findById(id);
        
        if(jogo.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        jogoRepository.deleteById(id);              
    }

}
