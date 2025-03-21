package com.fiap.sprint.model.paciente;

import com.fiap.sprint.model.endereço.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CadastroPacienteForm {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private LocalDate data_nascimento;

    @NotNull
    private Genero genero;

    @NotBlank
    private String telefone;

    @NotNull
    @Valid
    private EnderecoDTO endereco = new EnderecoDTO("", "", "", "", "", "", ""); // Inicialize aqui

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    // Método para converter para CadastroPacienteDTO
    public CadastroPacienteDTO toCadastroPacienteDTO() {
        return new CadastroPacienteDTO(nome, email, data_nascimento, genero, telefone, endereco);
    }
}