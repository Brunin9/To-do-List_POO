package br.edu.ifpr.todo.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.todo.api.dto.TarefaRequest;
import br.edu.ifpr.todo.api.dto.TarefaResponse;
import br.edu.ifpr.todo.domain.model.Tarefa;
import br.edu.ifpr.todo.domain.model.TodoStatus;
import br.edu.ifpr.todo.domain.service.TarefaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefas")
// @CrossOrigin(origins = "*") // Libere se for consumir de um front rodando
public class TarefaController {
  
  private final TarefaService service;

  public TarefaController(TarefaService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<TarefaResponse> criar(@Valid @RequestBody TarefaRequest dto) {
    Tarefa novaTarefa = service.criar(dto);
    TarefaResponse response = converterParaResponse(novaTarefa);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<TarefaResponse>> listar(
      @RequestParam(required = false) TodoStatus status,
      @RequestParam(required = false) Boolean importante) {
    
    List<Tarefa> tarefas = service.listar(null, status, importante, null);
    List<TarefaResponse> responses = tarefas.stream()
        .map(this::converterParaResponse)
        .collect(Collectors.toList());
    
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TarefaResponse> buscarPorId(@PathVariable Long id) {
    Tarefa tarefa = service.buscarPorId(id);
    return ResponseEntity.ok(converterParaResponse(tarefa));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TarefaResponse> atualizarParcial(
      @PathVariable Long id, 
      @Valid @RequestBody TarefaRequest dto) {
    
    Tarefa tarefaAtualizada = service.atualizarParcial(id, dto);
    return ResponseEntity.ok(converterParaResponse(tarefaAtualizada));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    service.excluir(id);
    return ResponseEntity.noContent().build();
  }

  // Método auxiliar para converter Tarefa em TarefaResponse
  private TarefaResponse converterParaResponse(Tarefa tarefa) {
    return new TarefaResponse(
        tarefa.getId(),
        tarefa.getNome(),
        tarefa.getDescricao(),
        tarefa.getStatus(),
        tarefa.getDataCriacao(),
        tarefa.getDataEntrega(),
        tarefa.getImportante()
    );
  }
}