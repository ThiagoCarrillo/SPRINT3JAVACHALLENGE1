package com.fiap.sprint.controller;

import com.fiap.sprint.model.tratamento.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tratamentos")
public class TratamentoController {

    @Autowired
    private TratamentoService tratamentoService;

    // Exibe o formulário de criação de tratamento
    @GetMapping("/create")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("tratamento", new TratamentoDTO("", "", Tipo.CANAL)); // Objeto vazio para o formulário
        return "tratamentos/create"; // Retorna a view create.html
    }

    // Processa o formulário de criação de tratamento
    @PostMapping
    public String cadastrar(@ModelAttribute @Valid TratamentoDTO dados, RedirectAttributes redirectAttributes) {
        var tratamento = tratamentoService.cadastrar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Tratamento cadastrado com sucesso!");
        return "redirect:/tratamentos"; // Redireciona para a lista de tratamentos
    }

    // Lista todos os tratamentos
    @GetMapping
    public String listar(@PageableDefault(size = 10, sort = {"tipo"}) Pageable paginacao, Model model) {
        Page<ListaTratamentoDTO> tratamentos = tratamentoService.listar(paginacao).map(ListaTratamentoDTO::new);
        model.addAttribute("tratamentos", tratamentos);
        return "tratamentos/index"; // Retorna a view index.html
    }

    // Exibe os detalhes de um tratamento específico
    @GetMapping("/{id}")
    public String detalhar(@PathVariable @Min(1) Long id, Model model) {
        var tratamento = tratamentoService.detalhar(id);
        model.addAttribute("tratamento", tratamento);
        return "tratamentos/detalhes"; // Retorna a view detalhes.html
    }

    // Exibe o formulário de edição de tratamento
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicao(@PathVariable @Min(1) Long id, Model model) {
        var tratamento = tratamentoService.detalhar(id);
        model.addAttribute("tratamento", tratamento);
        return "tratamentos/edit"; // Retorna a view edit.html
    }


    // Processa o formulário de edição de tratamento
    @PostMapping("/edit/{id}")
    public String atualizar(@PathVariable @Min(1) Long id, @ModelAttribute @Valid AttTratamentoDTO dados, RedirectAttributes redirectAttributes) {
        var tratamento = tratamentoService.atualizar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Tratamento atualizado com sucesso!");
        return "redirect:/tratamentos"; // Redireciona para a lista de tratamentos
    }

    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tratamentoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Tratamento excluído com sucesso!");
            return "redirect:/tratamentos";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("erro", "Tratamento não encontrado!");
            return "redirect:/tratamentos";
        }
    }
}