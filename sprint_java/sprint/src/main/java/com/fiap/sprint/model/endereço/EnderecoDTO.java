package com.fiap.sprint.model.endereço;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

//cLASSE PARA TRANSFERENCIA DE DADOS
public record EnderecoDTO(
        @NotBlank(message = "O logradouro não deve estar em branco")
        String logradouro,

        @NotBlank(message = "O bairro não deve estar em branco")
        String bairro,

        @NotBlank(message = "O CEP não deve estar em branco")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve ter 8 dígitos")
        String cep,

        @NotBlank(message = "A cidade não deve estar em branco")
        String cidade,

        @NotBlank(message = "A UF não deve estar em branco")
        String uf,

        String complemento,

        String numero
) {}
