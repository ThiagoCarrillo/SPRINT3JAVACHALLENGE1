package com.fiap.sprint.model.tratamento;


import jakarta.validation.constraints.*;

public record AttTratamentoDTO(
        @NotNull(message = "O ID não pode ser nulo")
        @Positive(message = "O ID deve ser um número positivo") Long id,

        @NotBlank(message = "A descrição não pode estar em branco")
        @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres") String descricao,

        @NotBlank(message = "O custo não pode estar em branco")
        @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "O custo deve ser um número válido (ex: 10.99)") String custo,

        @NotNull(message = "O tipo não pode ser nulo") Tipo tipo
) {}
