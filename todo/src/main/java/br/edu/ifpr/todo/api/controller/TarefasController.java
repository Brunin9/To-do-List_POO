package br.edu.ifpr.todo.api.controller;

import br.edu.ifpr.todo.api.dto.TarefaRequest;
import br.edu.ifpr.todo.api.dto.TarefaResponse;
import br.edu.ifpr.todo.domain.model.Tarefa;
import br.edu.ifpr.todo.domain.model.TodoStatus;
import br.edu.ifpr.todo.domain.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarefas")
public class TarefasController {

    private final TarefaService tarefaService;

    public TarefasController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    // Criar nova tarefa
    @PostMapping
    public ResponseEntity<TarefaResponse> salvar(@Valid @RequestBody TarefaRequest requisicao) {
        Tarefa tarefaCriada = tarefaService.criar(requisicao);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(tarefaCriada));
    }

    // Listar tarefas (com filtros opcionais)
    @GetMapping
    public ResponseEntity<List<TarefaResponse>> buscarTodas(
            @RequestParam(required = false) TodoStatus status,
            @RequestParam(required = false) Boolean importante) {

        List<Tarefa> resultado = tarefaService.listar(null, status, importante, null);

        List<TarefaResponse> resposta = resultado.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> buscarPorId(@PathVariable Long id) {
        Tarefa encontrada = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(toResponse(encontrada));
    }

    // Atualização parcial
    @PatchMapping("/{id}")
    public ResponseEntity<TarefaResponse> editar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaRequest requisicao) {

        Tarefa modificada = tarefaService.atualizarParcial(id, requisicao);
        return ResponseEntity.ok(toResponse(modificada));
    }

    // Deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // Conversão entidade -> DTO
    private TarefaResponse toResponse(Tarefa tarefa) {
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
