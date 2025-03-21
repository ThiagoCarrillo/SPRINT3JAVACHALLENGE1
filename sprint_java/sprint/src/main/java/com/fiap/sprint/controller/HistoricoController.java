package com.fiap.sprint.controller;

import com.fiap.sprint.model.historicoTratamento.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historico") // Caminho base no singular
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    // Exibe o formulário de criação de histórico
    @GetMapping("/create")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("historicoForm", new HistoricoDTO(null, null, null, ""));
        return "historico/create"; // Caminho corrigido
    }

    // Processa o formulário de criação de histórico
    @PostMapping("/create")
    public String cadastrar(@ModelAttribute @Valid HistoricoDTO historicoForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "historico/create"; // Caminho corrigido
        }

        historicoService.cadastrar(historicoForm);
        redirectAttributes.addFlashAttribute("mensagem", "Histórico cadastrado com sucesso!");
        return "redirect:/historico"; // Redireciona para a lista de históricos
    }

    // Lista todos os históricos
    @GetMapping
    public String listar(@PageableDefault(size = 10, sort = {"observacao"}) Pageable paginacao, Model model) {
        Page<ListaHistoricoDTO> historicos = historicoService.listar(paginacao).map(ListaHistoricoDTO::new);
        model.addAttribute("historicos", historicos);
        return "historico/index"; // Caminho corrigido
    }

    // Exibe o formulário de edição de histórico
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        var historico = historicoService.detalhar(id);
        model.addAttribute("historico", new AttHistoricoDTO(historico.getId(), historico.getObservacao()));
        return "historico/edit"; // Caminho corrigido
    }

    // Processa o formulário de edição de histórico
    @PostMapping("/edit/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute @Valid AttHistoricoDTO dados, RedirectAttributes redirectAttributes) {
        historicoService.atualizar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Histórico atualizado com sucesso!");
        return "redirect:/historico"; // Redireciona para a lista de históricos
    }

    // Exclui um histórico
    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        historicoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Histórico excluído com sucesso!");
        return "redirect:/historico"; // Redireciona para a lista de históricos
    }

    // Exibe os detalhes de um histórico
    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        var historico = historicoService.detalhar(id);
        model.addAttribute("historico", new ListaHistoricoDTO(historico));
        return "historico/detalhes"; // Caminho corrigido
    }
}