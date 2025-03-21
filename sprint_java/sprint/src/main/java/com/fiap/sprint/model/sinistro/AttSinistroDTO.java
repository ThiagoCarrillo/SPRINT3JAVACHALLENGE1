package com.fiap.sprint.model.sinistro;


import jakarta.validation.constraints.NotNull;

public record AttSinistroDTO(
        @NotNull Long id,
        String descricao,
        String reembolso, // Mapeado para valor_reembolsado no banco
        String status
) {}