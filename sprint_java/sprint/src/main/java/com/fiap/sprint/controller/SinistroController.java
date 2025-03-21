package com.fiap.sprint.controller;

import com.fiap.sprint.model.sinistro.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sinistros")
public class SinistroController {

    @Autowired
    private SinistroService sinistroService;

    // Exibe o formulário de criação de sinistro
    @GetMapping("/create")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("sinistroForm", new SinistroDTO(null, null, "", null, "", ""));
        return "sinistros/create"; // Retorna a view create.html
    }

    // Processa o formulário de criação de sinistro
    @PostMapping
    public String cadastrar(@ModelAttribute @Valid SinistroDTO dados, RedirectAttributes redirectAttributes) {
        var sinistro = sinistroService.cadastrar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Sinistro cadastrado com sucesso!");
        return "redirect:/sinistros"; // Redireciona para a lista de sinistros
    }

    // Lista todos os sinistros
    @GetMapping
    public String listar(@PageableDefault(size = 10, sort = {"status"}) Pageable paginacao, Model model) {
        Page<ListaSinistroDTO> sinistros = sinistroService.listar(paginacao).map(ListaSinistroDTO::new);
        model.addAttribute("sinistros", sinistros);
        return "sinistros/index"; // Retorna a view index.html
    }

    // Exibe os detalhes de um sinistro específico
    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        var sinistro = sinistroService.detalhar(id);
        model.addAttribute("sinistro", sinistro);
        return "sinistros/detalhes"; // Retorna a view detalhes.html
    }

    // Exibe o formulário de edição de sinistro
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        var sinistro = sinistroService.detalhar(id);
        model.addAttribute("sinistro", sinistro);
        return "sinistros/edit"; // Retorna a view edit.html
    }

    // Processa o formulário de edição de sinistro
    @PostMapping("/edit/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute @Valid AttSinistroDTO dados, RedirectAttributes redirectAttributes) {
        var sinistro = sinistroService.atualizar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Sinistro atualizado com sucesso!");
        return "redirect:/sinistros"; // Redireciona para a lista de sinistros
    }

    // Exclui um sinistro
    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sinistroService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Sinistro excluído com sucesso!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("erro", "Sinistro não encontrado!");
        }
        return "redirect:/sinistros"; // Redireciona para a lista de sinistros
    }
}