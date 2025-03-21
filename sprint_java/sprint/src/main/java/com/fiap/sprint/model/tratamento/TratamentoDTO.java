package com.fiap.sprint.model.tratamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//cLASSE PARA TRANSFERENCIA DE DADOS
public record TratamentoDTO(@NotBlank String descricao, @NotBlank String custo, @NotNull Tipo tipo){


    public TratamentoDTO(Tratamento tratamento) {
        this( tratamento.getDescricao(), tratamento.getCusto(), tratamento.getTipo());
    }
}
