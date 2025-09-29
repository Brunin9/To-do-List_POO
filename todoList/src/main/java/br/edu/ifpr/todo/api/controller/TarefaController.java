package br.edu.ifpr.todo.api.controller;

import br.edu.ifpr.todo.api.dto.TarefaRequest;
import br.edu.ifpr.todo.api.dto.TarefaResponse;
import br.edu.ifpr.todo.domain.model.Tarefa;
import br.edu.ifpr.todo.domain.model.TodoStatus;
import br.edu.ifpr.todo.domain.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
// @CrossOrigin(origins = "*") // Libere se for consumir de um front rodando

public class TarefaController {
  private final TarefaService service;

  public TarefaController(TarefaService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TarefaResponse criar(@Valid @RequestBody TarefaRequest dto) {
    Tarefa salva = service.criar(dto);
    return new TarefaResponse(
        salva.getId(),
        salva.getNome(),
        salva.getDescricao(),
        salva.getStatus(),
        salva.getDataCriacao(),
        salva.getDataEntrega(),
        salva.getImportante());
  }

  @GetMapping
  public List<TarefaResponse> listar(
      @RequestParam(required = false) TodoStatus status,
      @RequestParam(required = false) Boolean importante) {
    List<Tarefa> tarefas = service.listar(null, status, importante, null);
    return tarefas.stream()
        .map(t -> new TarefaResponse(
            t.getId(),
            t.getNome(),
            t.getDescricao(),
            t.getStatus(),
            t.getDataCriacao(),
            t.getDataEntrega(),
            t.getImportante()))
        .toList();
  }

  @GetMapping("/{id}")
  public TarefaResponse buscarPorId(@PathVariable Long id) {
    Tarefa tarefa = service.buscarPorId(id);
    return new TarefaResponse(
        tarefa.getId(),
        tarefa.getNome(),
        tarefa.getDescricao(),
        tarefa.getStatus(),
        tarefa.getDataCriacao(),
        tarefa.getDataEntrega(),
        tarefa.getImportante());
  }

  @PatchMapping("/{id}")
  public TarefaResponse atualizarParcial(@PathVariable Long id, @Valid @RequestBody TarefaRequest dto) {
    Tarefa atualizada = service.atualizarParcial(id, dto);
    return new TarefaResponse(
        atualizada.getId(),
        atualizada.getNome(),
        atualizada.getDescricao(),
        atualizada.getStatus(),
        atualizada.getDataCriacao(),
        atualizada.getDataEntrega(),
        atualizada.getImportante());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    service.excluir(id);
  }
}
