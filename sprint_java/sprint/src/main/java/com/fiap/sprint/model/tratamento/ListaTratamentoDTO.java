package com.fiap.sprint.model.tratamento;

public record ListaTratamentoDTO(Long id, String descricao, String custo, Tipo tipo) {

    public ListaTratamentoDTO(Tratamento tratamento) {
        this(tratamento.getId(), tratamento.getDescricao(), tratamento.getCusto(), tratamento.getTipo());
    }

}
