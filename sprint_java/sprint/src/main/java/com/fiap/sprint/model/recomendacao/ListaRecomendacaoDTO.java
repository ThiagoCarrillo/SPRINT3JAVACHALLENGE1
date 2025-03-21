package com.fiap.sprint.model.recomendacao;

import java.time.LocalDate;

public record ListaRecomendacaoDTO(
        Long id,
        Long pacienteId,
        Long tratamentoId,
        LocalDate data_recomendacao,
        String motivo) {

    public ListaRecomendacaoDTO(Recomendacao recomendacao) {
        this(
                recomendacao.getId(),
                recomendacao.getPaciente() != null ? recomendacao.getPaciente().getId() : null,
                recomendacao.getTratamento() != null ? recomendacao.getTratamento().getId() : null,
                LocalDate.from(recomendacao.getData_recomendacao()),
                recomendacao.getMotivo() != null ? recomendacao.getMotivo().toString() : null
        );
    }
}