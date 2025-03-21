package com.fiap.sprint.model.sinistro;

import java.time.LocalDate;

public record ListaSinistroDTO(
        Long id,
        String descricao,
        String status,
        Long pacienteId,
        Long tratamentoId,
        LocalDate data_ocorrencia,
        String reembolso) {

    public ListaSinistroDTO(Sinistro sinistro) {
        this(
                sinistro.getId(),
                sinistro.getDescricao(),
                sinistro.getStatus() != null ? sinistro.getStatus().toString() : null,
                sinistro.getPaciente() != null ? sinistro.getPaciente().getId() : null,
                sinistro.getTratamento() != null ? sinistro.getTratamento().getId() : null,
                LocalDate.from(sinistro.getData_ocorrencia()),
                sinistro.getReembolso() != null ? sinistro.getReembolso().toString() : null
        );
    }
}