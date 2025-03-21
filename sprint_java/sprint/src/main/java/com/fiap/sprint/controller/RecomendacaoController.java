package com.fiap.sprint.controller;

import com.fiap.sprint.model.recomendacao.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;


// Classe que recebe as requisições do CRUD
@Controller
@RequestMapping("/recomendacao")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping
    public String listar(@PageableDefault(size = 10, sort = {"motivo"}) Pageable paginacao, Model model) {
        Page<ListaRecomendacaoDTO> recomendacoes = recomendacaoService.listar(paginacao).map(ListaRecomendacaoDTO::new);
        model.addAttribute("recomendacoes", recomendacoes);
        return "recomendacao/index"; // Retorna a view index.html
    }

    @GetMapping("/create")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("recomendacaoForm", new RecomendacaoDTO(null, null, null, ""));
        return "recomendacao/create"; // Retorna a view create.html
    }

    @PostMapping
    public String cadastrar(@ModelAttribute @Valid RecomendacaoDTO dados, RedirectAttributes redirectAttributes) {
        var recomendacao = recomendacaoService.cadastrar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Recomendação cadastrada com sucesso!");
        return "redirect:/recomendacao"; // Redireciona para a lista de recomendações
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        var recomendacao = recomendacaoService.detalhar(id);
        model.addAttribute("recomendacao", recomendacao);
        return "recomendacao/edit"; // Retorna a view edit.html
    }

    @PostMapping("/edit/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute @Valid AttRecomendacaoDTO dados, RedirectAttributes redirectAttributes) {
        var recomendacao = recomendacaoService.atualizar(dados);
        redirectAttributes.addFlashAttribute("mensagem", "Recomendação atualizada com sucesso!");
        return "redirect:/recomendacao"; // Redireciona para a lista de recomendações
    }

    @PostMapping("/delete/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recomendacaoService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Recomendação excluída com sucesso!");
        return "redirect:/recomendacao"; // Redireciona para a lista de recomendações
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        var recomendacao = recomendacaoService.detalhar(id);
        model.addAttribute("recomendacao", recomendacao);
        return "recomendacao/detalhes"; // Retorna a view detalhes.html
    }

    @GetMapping("/delete/{id}")
    public String confirmarExclusao(@PathVariable Long id, Model model) {
        var recomendacao = recomendacaoService.detalhar(id);
        model.addAttribute("recomendacao", recomendacao);
        return "recomendacao/delete"; // Retorna a view delete.html
    }


}