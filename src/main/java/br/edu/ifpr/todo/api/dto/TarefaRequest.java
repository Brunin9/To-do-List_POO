package br.edu.ifpr.todo.api.dto;

import br.edu.ifpr.todo.domain.model.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TarefaRequest {
    @NotBlank
    @Size(max = 120)
    private String nome;

    @Size(max = 500)
    private String descricao;

    private TodoStatus status;
    private LocalDate dataEntrega;
    private Boolean importante;

    // Getters e Setters
}
