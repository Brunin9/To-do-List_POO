package br.edu.ifpr.todo.api.dto;

import br.edu.ifpr.todo.domain.model.TodoStatus;
import java.time.LocalDate;

public class TarefaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private TodoStatus status;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private Boolean importante;

    public TarefaResponse(Long id, String nome, String descricao, TodoStatus status,
                          LocalDate dataCriacao, LocalDate dataEntrega, Boolean importante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataEntrega = dataEntrega;
        this.importante = importante;
    }

    // Getters e Setters
}
