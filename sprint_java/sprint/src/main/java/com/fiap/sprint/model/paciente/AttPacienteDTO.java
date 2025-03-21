package com.fiap.sprint.model.paciente;


import com.fiap.sprint.model.endereço.EnderecoDTO;
import jakarta.validation.constraints.NotNull;

public record AttPacienteDTO(@NotNull Long id, String nome, String telefone, String email, EnderecoDTO endereco) {


}
