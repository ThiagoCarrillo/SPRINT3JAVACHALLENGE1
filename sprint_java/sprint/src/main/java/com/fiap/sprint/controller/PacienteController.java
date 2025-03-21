package com.fiap.sprint.controller;

import com.fiap.sprint.model.endereço.EnderecoDTO;
import com.fiap.sprint.model.paciente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Exibe o formulário de criação de paciente
    @GetMapping("/create")
    public String mostrarFormularioCriacao(Model model) {
        CadastroPacienteForm form = new CadastroPacienteForm();

        // Inicialize o EnderecoDTO com valores padrão ou vazios
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "", // logradouro
                "", // bairro
                "", // cep
                "", // cidade
                "", // uf
                "", // complemento
                ""  // numero
        );
        form.setEndereco(enderecoDTO);

        // Adicione o objeto ao modelo
        model.addAttribute("pacienteForm", form);
        return "pacientes/create";
    }

    // Processa o formulário de criação de paciente
    @PostMapping("/create") // Caminho relativo ao @RequestMapping("/pacientes")
    public String cadastrar(@ModelAttribute @Valid CadastroPacienteForm pacienteForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Se houver erros de validação, retorne para o formulário com mensagens de erro
            return "pacientes/create";
        }

        // Processa o cadastro do paciente
        var dados = pacienteForm.toCadastroPacienteDTO();
        var paciente = pacienteService.cadastrar(dados);

        // Adiciona uma mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Paciente cadastrado com sucesso!");

        // Redireciona para a lista de pacientes
        return "redirect:/pacientes";
    }

    // Lista todos os pacientes
    @GetMapping
    public String listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao, Model model) {
        Page<ListaPacienteDTO> pacientes = pacienteService.listar(paginacao).map(ListaPacienteDTO::new);
        model.addAttribute("pacientes", pacientes);
        return "pacientes/index";
    }

    // Exibe os detalhes de um paciente específico
    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        var paciente = pacienteService.detalhar(id);
        model.addAttribute("paciente", new DetalharPacienteDTO(paciente));
        return "pacientes/detalhes";
    }

    // Exibe o formulário de edição de paciente
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        var paciente = pacienteService.detalhar(id);
        model.addAttribute("paciente", new AttPacienteDTO(paciente.getId(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail(), null));
        return "pacientes/edit";
    }

    // Processa o formulário de edição de paciente
    @PostMapping("/edit/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute @Valid AttPacienteDTO dados, RedirectAttributes redirectAttributes) {
        var paciente = pacienteService.atualizar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Paciente atualizado com sucesso!");
        return "redirect:/pacientes";
    }

    // Exclui um paciente
    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pacienteService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Paciente excluído com sucesso!");
        return "redirect:/pacientes";
    }
}